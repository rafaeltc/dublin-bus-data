db.createUser({
    user: "username",
    pwd: "secret",
    roles: [{
        role: "readWrite",
        db: "your-database-name"
    }]
})