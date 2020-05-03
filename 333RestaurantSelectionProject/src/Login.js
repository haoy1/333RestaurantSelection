
const loginForm = document.getElementById("login-form");
const loginButton = document.getElementById("login-form-submit");
const loginErrorMsg = document.getElementById("login-error-msg");
const connectedMsg = document.getElementById("connection-msg");

loginButton.addEventListener("click", (e) => {
    e.preventDefault();
    const username = loginForm.username.value;
    const password = loginForm.password.value;

    if (username === "user" && password === "web_dev") {
        alert("You have successfully logged in.");
        location.reload();
    } else {
    	loginErrorMsg.style.opacity = 1;
    }
})

var mysql = require('mysql');

var con = mysql.createConnection({
  host: "golem.csse.rose-hulman.edu",
  user: "haoy1@rose-hulman.edu",
  password: "Horryno1",
  database: "RestaurantSelection"
});

con.connect(function(err) { 
  if (err) {
	  console.error(err);
	  return;
  }
  console.log("Connected!");
  connectedMsg.style.opacity = 1;
  con.query(sql, [values], function (err, result) {
    if (err) throw err;
    console.log("Number of records inserted: " + result.affectedRows);
  });
});