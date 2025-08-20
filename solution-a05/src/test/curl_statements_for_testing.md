

# 1
```
curl -X PUT localhost:8005/courses/XROAD/enrollments \
-H 'Content-type:application/json' \
-d '{  "course": { "courseId": "BEGINNER_SPRING_BOOT" },
"student": { "personId": 2 }, "enrollmentDate": "2025-02-01" }'
```

# 2
```
curl -v -X PUT localhost:8005/courses/XROAD/enrollments \
-H 'Content-type:application/json' \
-d '{ "course": { "courseId": "XROAD" }, 
"student": { "personId": 1 }, "enrollmentDate": "2010-12-13" }’
```

# 3

```
curl -v -X PUT localhost:8005/courses/XROAD/enrollments \
-H 'Content-type:application/json' \
-d '{  "course": { "courseId": "XROAD" },
"student": { "personId": 1, "name": "John Doe" },
"enrollmentDate": "2010-12-13" }'
```
