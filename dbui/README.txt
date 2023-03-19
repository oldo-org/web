# web-cruddy-bee
Generic Web CRUD from RDBMS metadata 

Initial implementation will use Postgres as database server
Use vertx-web for serving http requests : https://vertx.io/docs/vertx-web/java/
HTML generation using HtmlFlow library, no JS.
Generate standard SQL, simply: insert/update single row, select * from table where ..., delete from table where pk = ...
Otherwise consider JOOQ for SQL generation : https://www.jooq.org/doc/3.14/manual/getting-started/use-cases/jooq-as-a-sql-builder-without-codegeneration/

- Provides Web HTML forms and index/detail navigation and CRUD operations
- Dynamically based on column and table metadata via JDBC (see DatabaseMetaData, ResultSetMetaData)
- Metadata cached on startup and refreshed on demand (if db schema changed at runtime)
- No data model in Java (no domain classes, no ORM)
- Generates entry forms using SQL column types, column metadata (nullable, check constraints, etc)
- HTML input types and relationship navigation are chosen by convention 
 - See https://www.w3schools.com/tags/tag_input.asp
 - radio buttons, combo boxes for smaller or bigger finite value set properties based on check constraint
 - checkboxes for bit and boolean types
 - number fields with min/max from check constraints
 - dates / times using corresponding html input types
 - char types : use maxlength and regex from check constraints for client side validation
 - html anchors to other forms for foreign key columns (using fk target metadata and fk value in url)
 - TBD: How to add/change fk values during create/update?
 - HTML Search form using metadata for generating search fields and operators
 - Free form Search using a SQL entry field => URL with query as param
 
 Base URL pattern: http://site/schema/table/ and follow RESTful approach
 
 Generated SQL

