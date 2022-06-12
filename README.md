# HDBFS
* JWT Role based authentication you will generate token for two roles 'user' and 'admin'

* Only 'admin' role can access second API. So using previously generated token you will access second API for PDF generation.
* Maintaining logs in database. Log will contain user name, password  (encrypted hash value ), user role, request body, response body, logging timestamp.
