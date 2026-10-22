package com.example.app;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.CookieManager;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

/** Drives the running application over real HTTP, as a browser posting the form would. */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class NotesHttpTests {

	private static final Pattern CSRF_TOKEN = Pattern.compile("name=\"_csrf\" value=\"([^\"]+)\"");

	@LocalServerPort
	private int port;

	@Autowired
	private NoteRepository notes;

	private HttpClient client;

	@BeforeEach
	void setUp() {
		notes.deleteAll();
		client = HttpClient.newBuilder().cookieHandler(new CookieManager()).build();
	}

	@Test
	void reportsHealth() throws Exception {
		HttpResponse<String> health = get("/actuator/health");

		assertThat(health.statusCode()).isEqualTo(200);
		assertThat(health.body()).contains("\"status\":\"UP\"");
	}

	@Test
	void savesANotePostedWithTheFormToken() throws Exception {
		HttpResponse<String> home = get("/");
		assertThat(home.statusCode()).isEqualTo(200);
		assertThat(home.body()).contains("No notes yet.");

		HttpResponse<String> saved = postNote(csrfToken(home.body()), "Hello from the form", "notes.example.test");

		assertThat(saved.statusCode()).isEqualTo(302);
		assertThat(saved.headers().firstValue("Location")).hasValue("/");
		assertThat(notes.findAll()).extracting(Note::getBody).containsExactly("Hello from the form");
		assertThat(get("/").body()).contains("Hello from the form").doesNotContain("No notes yet.");
	}

	@Test
	void rejectsAPostWithoutTheFormToken() throws Exception {
		get("/");

		HttpResponse<String> rejected = postNote(null, "Forged", null);

		assertThat(rejected.statusCode()).isEqualTo(403);
		assertThat(notes.count()).isZero();
	}

	@Test
	void showsValidationErrorsWithoutSaving() throws Exception {
		HttpResponse<String> home = get("/");

		HttpResponse<String> invalid = postNote(csrfToken(home.body()), "   ", null);

		assertThat(invalid.statusCode()).isEqualTo(422);
		assertThat(invalid.body()).contains("Write something first.");
		assertThat(notes.count()).isZero();
	}

	private HttpResponse<String> get(String path) throws IOException, InterruptedException {
		return client.send(HttpRequest.newBuilder(uri(path)).GET().build(), HttpResponse.BodyHandlers.ofString());
	}

	private HttpResponse<String> postNote(String token, String body, String forwardedHost)
			throws IOException, InterruptedException {
		String form = "body=" + URLEncoder.encode(body, StandardCharsets.UTF_8);
		if (token != null) {
			form += "&_csrf=" + URLEncoder.encode(token, StandardCharsets.UTF_8);
		}
		HttpRequest.Builder request = HttpRequest.newBuilder(uri("/notes"))
			.header("Content-Type", "application/x-www-form-urlencoded")
			.POST(HttpRequest.BodyPublishers.ofString(form));
		if (forwardedHost != null) {
			// A same-machine reverse proxy: the redirect must not point at the loopback address.
			request.header("X-Forwarded-Host", forwardedHost)
				.header("X-Forwarded-Proto", "https")
				.header("X-Forwarded-Port", "443");
		}
		return client.send(request.build(), HttpResponse.BodyHandlers.ofString());
	}

	private URI uri(String path) {
		return URI.create("http://127.0.0.1:" + port + path);
	}

	private static String csrfToken(String html) {
		Matcher matcher = CSRF_TOKEN.matcher(html);
		assertThat(matcher.find()).as("form carries a CSRF token").isTrue();
		return matcher.group(1);
	}

}
