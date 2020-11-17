# misc

###Records
####API
To start the API
```
lein records-api
``` 

The following endpoints are supported
```
GET /records/gender
GET /records/birthdate
GET /records/name
POST /records
```

Records have the format
```json
{"firstName": "harry",
 "lastName": "potter",
 "gender": "m",
 "favoriteColor": "red",
 "birthdate": "1993-01-29"}
```

```
firstName- must be a string
lastName - must be a string
gender - must be 'm' or 'f'
favoriteColor - must be a string
birthdate - must be in the format 'mm/dd/yyyy'
```

####CLI
To parse and print out the records in a file, use
```
lein records-cli -f <path-to-file> -s <sort> 

sort - can be either `gender`, `birthdate` or `name`
(if none is provided, `gender` is used as a default)
```