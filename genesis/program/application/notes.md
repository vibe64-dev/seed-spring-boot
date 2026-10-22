# Notes

Visitors read saved notes and add new ones from the home page.

## Sources

- Main: `src/main/java/com/example/app/HomeController.java`
- `src/main/java/com/example/app/Note.java`
- `src/main/java/com/example/app/NoteForm.java`
- `src/main/java/com/example/app/NoteRepository.java`
- `src/main/java/com/example/app/SecurityConfig.java`
- `src/main/resources/templates/home.html`
- `src/main/resources/static/app.css`
- `src/main/resources/db/migration/V1__create_notes.sql`
- `src/test/java/com/example/app/NoteFormTests.java`
- `src/test/java/com/example/app/NotesHttpTests.java`

## Public contract

The home page lists the 50 most recent notes, newest first, each with its
saving time in UTC, or says there are none yet. It always shows the note form.

Posting the form saves the note's text, without surrounding whitespace, and
redirects back to the home page. A note must contain text and be at most 500
characters; otherwise the home page is shown again with the reason and an
unprocessable-content status, and nothing is saved. A post without the form's
anti-forgery token is refused and saves nothing.

No sign-in is required. Redirects stay on the address the visitor used, also
behind a reverse proxy.
