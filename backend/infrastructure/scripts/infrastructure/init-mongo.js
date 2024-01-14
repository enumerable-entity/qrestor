var conn = new Mongo();
var db = conn.getDB('menu');

db.createUser(
    {
        user: 'menu',
        pwd: 'menu',
        roles: [
            {
                role: "readWrite",
                db: 'menu'
            }
        ]
    }
);
db = db.getSiblingDB('menu')
db.createCollection('preparedMenus');

print("Initialization completed successfully!");
