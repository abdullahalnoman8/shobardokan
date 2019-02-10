
var token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
console.log(token);


AccountKit_OnInteractive = function(){
    AccountKit.init(
        {
            appId:186265032284393,
            state:token,
            version:"v3.2",
            fbAppEventsEnabled:true,
            redirect:"https://localhost:8443/dashboard/",
            debug: true
        }
    );
};

// login callback
function loginCallback(response) {
    if (response.status === "PARTIALLY_AUTHENTICATED") {
        var code = response.code;
        var csrf = response.state;
        console.log( "Code: "+ code + "\n CSRF Token : " +csrf);
        // Send code to server to exchange for access token
        console.log(response);
        otpInformation(code,csrf);
    }
    else if (response.status === "NOT_AUTHENTICATED") {
        // handle authentication failure
        console.log(response.status);
    }
    else if (response.status === "BAD_PARAMS") {
        // handle bad parameters
        console.log(response.status);
    }
}

function otpInformation(code,csrf) {
    console.log("Code: "+code);
    console.log("\n CSRF: "+csrf);
    $.ajax({
        type: "GET",
        dataType: "text",
        url: "/otpData",
        data : {
            code: code,
            csrf: csrf
        },
        success: function(response){
            console.log("This is response from controller" + response);
        },
        error: function(xhr, error){
            console.log("Error Found :{}",error);
            console.debug(xhr); console.debug(error);
        }
    });
}

// phone form submission handler
function smsLogin() {
    var countryCode = document.getElementById("country_code").value;
    var phoneNumber = document.getElementById("phone_number").value;
    console.log("Country Code: ",countryCode);
    console.log("Phone Number: ",phoneNumber);
    AccountKit.login(
        'PHONE',
        {countryCode: countryCode, phoneNumber: phoneNumber}, // will use default values if not specified
        loginCallback
    );
}


// email form submission handler
function emailLogin() {
    var emailAddress = document.getElementById("email").value;
    AccountKit.login(
        'EMAIL',
        {emailAddress: emailAddress},
        loginCallback
    );
}