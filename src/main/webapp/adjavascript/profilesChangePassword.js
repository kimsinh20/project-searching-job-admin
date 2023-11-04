const getAlias = (str) => {
    if(str.length<=0) return;
    let arr = str.split(" ").reverse();
    let rs = "";
    for (let i = 0; i < arr.length; i++) {
        if (i >= 1) {
            rs += arr[i][0].toString().toUpperCase();
        } else {
            let name = arr[0].toString();
            rs+=name.charAt(0).toUpperCase()+name.slice(1);
        }
    }
    return rs;
}

let fullName = document.getElementById("fullName");
let alias = document.getElementById("alias");
if(fullName!==null && alias!==null) {
	window.addEventListener("load", (e) => {
    alias.value  = getAlias(fullName.value.trim());
    });

fullName.addEventListener("input", (e) => {
   e.preventDefault();
    alias.value  = getAlias(fullName.value.trim());
});
}



// valid password
// dom 
const btn_regiter = document.getElementById("btnChangePass");
// valid form function
const checkValidRegister = () => {
    //tham chieu lay du lieu
    let currentPassword = document.getElementById("currentPassword").value;
    let password = document.getElementById("newPassword").value;
    let comfirmPass = document.getElementById("renewPassword").value;
    
     if (currentPassword.length > 0) {
        document.getElementById('crpass').classList.remove('d-none');
    } else {
        document.getElementById('crpass').classList.add('d-none');
    }
     if (password.length > 0) {
        document.getElementById('npass').classList.remove('d-none');
    } else {
        document.getElementById('npass').classList.add('d-none');
    }
    if (comfirmPass.length > 0) {
        document.getElementById('firmpass').classList.remove('d-none');
    } else {
        document.getElementById('firmpass').classList.add('d-none');
    }
    // tham vi tri loi
    let viewErrName = document.getElementById("errpassword");
    let viewErrPass = document.getElementById("errnewpassword");
    let viewErrConfirmPass = document.getElementById("errrenewpassword");
    // bien xac nhan hop le cua gia tri
    let validName = true;
    let validPass = true;
    let validConfirmPass = true;

    // bien ghi nhan thong bao loi
    var message = '';
    currentPassword = currentPassword.trim(); // cat bo khoang cach
    if (currentPassword == '') {
        validName = false;
        message = 'Vui lòng nhập mật khẩu hiện tại';
    } else {
        if ((currentPassword.length < 5) || (currentPassword.length > 50)) {
            validName = false;
            message = 'mật khẩu hiện tại phải có chiều dài lớn hơn 5 ký tự';
        } else {
            if (currentPassword.indexOf(' ') != -1) {
                validName = false;
                message = 'mật khẩu không thể chứa dấu cách';
            }
        }
    }
    if (!validName) {
        viewErrName.innerHTML = message;

        //style css
        viewErrName.style.color = 'red';
        viewErrName.style.paddingTop = '10px';
        viewErrName.style.paddingBottom = '10px';
    } else {
	viewErrName.innerHTML = "";
	 viewErrName.style.paddingTop = '-10px';
        viewErrName.style.paddingBottom = '-10px';
}

    // valid pass work
    password = password.trim(); // cat bo khoang cach
    if (password == '') {
        validPass = false;
        message = 'Vui lòng nhập mật khẩu mới';
    } else {
        if ((password.length < 6)) {
            validPass = false;
            message = 'Mật khẩu mới chưa hợp lệ';
        }
    }
    if (!validPass) {
        viewErrPass.innerHTML = message;

        //style css
        viewErrPass.style.color = 'red';
        viewErrPass.style.paddingTop = '10px';
        viewErrPass.style.paddingBottom = '10px';
    } else {
	viewErrPass.innerHTML = "";
	viewErrPass.style.paddingTop = '-10px';
    viewErrPass.style.paddingBottom = '-10px';
}

    // valid comfirm pass
    comfirmPass = comfirmPass.trim(); // cat bo khoang cach
    if (comfirmPass == '') {
        validConfirmPass = false;
        message = 'Vui lòng nhập lại mật khẩu mới';
    } else {
        if ((comfirmPass.length < 6)) {
            validConfirmPass = false;
            message = 'Mật khẩu mới chưa khớp ';
        } else {
            if (comfirmPass != password) {
                validConfirmPass = false;
                message = 'Mật khẩu mới chưa trùng nhau';
            }
        }
    }
    if (!validConfirmPass) {
        viewErrConfirmPass.innerHTML = message;

        //style css
        viewErrConfirmPass.style.color = 'red';
        viewErrConfirmPass.style.paddingTop = '10px';
        viewErrConfirmPass.style.paddingBottom = '10px';
    } else {
	  viewErrConfirmPass.innerHTML = "";
	        viewErrConfirmPass.style.paddingTop = '-10px';
        viewErrConfirmPass.style.paddingBottom = '-10px';
    }
    
    // disabled button create
    let buttonCreate = document.getElementById("btnChangePass");
    if (validName && validConfirmPass && validPass) {
        buttonCreate.removeAttribute('disabled');
    } else {
        buttonCreate.setAttribute("disabled", true);
    }
    return validName && validPass && validConfirmPass;
}

//event
// onclick
//btn_regiter.addEventListener("click", (e) => {
  //  checkValidRegister();
//});

//generate password
function generatePassword() {
    var chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890@";
    var password = "";
    for (var i = 0; i < 10; i++) {
        password += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    
    document.getElementById("newPassword").value = password;
    document.getElementById("renewPassword").value = password;
    checkValidRegister();
    // window.alert(password);
    return true;
}
let currentPassword = document.getElementById("currentPassword");
let password = document.getElementById("newPassword");
let comfirmPass = document.getElementById("renewPassword");
if(currentPassword!==null) {
currentPassword.addEventListener("input", (e) => {
    e.preventDefault();
   checkValidRegister();
})
}
if(password!==null) {
password.addEventListener("input", (e) => {
    e.preventDefault();
   checkValidRegister();
})
}
if(comfirmPass!==null) {
comfirmPass.addEventListener("input", (e) => {
    e.preventDefault();
   checkValidRegister();
})
}
//show password
function togglePassword1() {
    let passwordField = document.getElementById("currentPassword");
    if (passwordField.type === "password") {
        passwordField.type = "text";
    } else {
        passwordField.type = "password";
    }
    // alert(passwordField)
}
function togglePassword2() {
    let newPassword = document.getElementById("newPassword");
    if (newPassword.type === 'password') {
        newPassword.type = 'text';
    } else {
        newPassword.type = "password";
    }
    // alert(passwordField)
}
function togglePassword3() {
    let comfirmPasswordField = document.getElementById("renewPassword");
    if (comfirmPasswordField.type === 'password') {
        comfirmPasswordField.type = 'text';
    } else {
        comfirmPasswordField.type = "password";
    }
    // alert(passwordField)
}
