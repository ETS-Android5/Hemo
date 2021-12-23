var admin = require("firebase-admin");

var serviceAccount = require("C:/Users/Asus/Documents/Avishkar21/Hemo/Hemo backend/notificationPrivate.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
});

var token = [
  "eHSHXU_mR6un-MAyf6gLTH:APA91bGgZL2UcXcn9-SdB3Zm5DaCXzLRkoPatkK5RAmc8WyNZd7zgne7pN4kniZcsXzN4ytLIHk3VfRwvllCyF8ta4yXYSbfrxsfpKkc4MipTmbGAVGb4Hxu-mdFkiD8ORbV5BcrjNoH",
];

var payload = {
  notification: {
    title: "Notification",
    body: "From Node js",
  },
};

var options = {
  priority: "high",
  timeToLive: 60 * 60 * 24,
};
admin
  .messaging()
  .sendToDevice(token, payload, options)
  .then(function (response) {
    console.log("Successfull sent message to device", response);
  })
  .catch(function (error) {
    console.log("Error sending message to device: " + error);
  });
