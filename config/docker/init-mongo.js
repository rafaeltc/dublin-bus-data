db.createUser({
    user: "username",
    pwd: "secret",
    roles: [{
        role: "readWrite",
        db: "your-database-name"
    }]
})

//use "your-database-name";

db.createCollection("data")
db.data.insert({ test: "true" })