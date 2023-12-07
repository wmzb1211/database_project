function validateForm() {
    var password1 = document.forms["registrationForm"]["password"].value;
    var password2 = document.forms["registrationForm"]["password2"].value;

    if (password1 !== password2) {
        alert("密码不匹配，请重新输入。");
        return false;
    }
    return true;
}