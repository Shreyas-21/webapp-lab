window.addEventListener("load", function() {
  const submit = document.getElementById('submit');
  const clearBtn = document.getElementById('clear');
  const username = document.getElementById('username');
  const phnum = document.getElementById('phone-number');
  const email = document.getElementById('email');
  const pwd = document.getElementById('psw');
  const pwd_rep = document.getElementById('psw-repeat');

  // As per the HTML5 Specification
  const emailRegExp = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
  const pwdRegExp = /(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/;
  const whitespaceRegExp = /\s/g;

  function usernameTest(username) {
    if (username === "")
      alert("Username cannot be empty");
    if (whitespaceRegExp.test(username))
      alert("Username cannot have whitespace");
  }

  function phnumTest(phnum) {
    if (phnum.toString().length !== 10)
      alert("Phone number must have 10 digits");
  }

  function emailTest(email) {
    if (!emailRegExp.test(email))
      alert("Not a valid email");
  }

  function pwdtest(pwd, pwd_rep) {
    if (pwd !== pwd_rep)
      alert("Passwords do not match");
    if(!pwdRegExp.test(pwd))
      alert("Password must have atleast 1 upper case letter, 1 lower case letter, 1 number or special \
        character, and atleast 8 characters long");
  }

  function clear(inpElem) {
    inpElem.value = "";
  }

  // This defines what happens when the user tries to submit the data
  submit.addEventListener("click", function () {
    usernameTest(username.value);
    phnumTest(phnum.value);
    emailTest(email.value);
    pwdtest(pwd.value, pwd_rep.value);    
  });

  clearBtn.addEventListener("click", function() {
    clear(username);
    clear(phnum);
    clear(email);
    clear(pwd);
    clear(pwd_rep);
  });
});
