## Creating customers with httpie

```bash
0 [17:41]leo@lein ~ $ http POST :8080/rc/customers id=1 name=Leo
HTTP/1.1 201 Created
Location: /rc/customers/28857d3a-166c-4ac5-8f25-31bde740cf5a
content-length: 0



0 [17:46]leo@lein ~ $ http :8080/rc/customers
HTTP/1.1 200 OK
Content-Type: application/json
transfer-encoding: chunked

[
    {
        "id": "28857d3a-166c-4ac5-8f25-31bde740cf5a",
        "name": "Leo"
    }
]
```
