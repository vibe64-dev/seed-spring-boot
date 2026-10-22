# Blueprint

A small public notes application. Visitors open the home page, write a short
note in a form and save it; saved notes stay in the database and appear newest
first, with the time they were saved. Blank or overlong notes are refused with
an explanation next to the field, and nothing is saved.

There are no accounts: anyone who can open the application can read and add
notes. Form submissions are protected against cross-site request forgery.

Operators can check whether the application is up and can reach its database.
