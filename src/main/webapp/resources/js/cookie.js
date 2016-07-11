/**
 * creates cookie
 */
function createCookie(name,value,days) {
    var expires = "";

    if (days) {
        var date = new Date();
        date.setTime(date.getTime()+(days*24*60*60*1000));
        expires = ";expires="+date.toGMTString();
    }

    document.cookie = name+"="+value+expires+";path=/";
}

/**
 * reads cookie
 */
function readCookie(name) {
    var nameEQ = name + "=true";
    var ca = document.cookie.split(';');
    var cookieState = 0;

    for(var i=0;i < ca.length;i++) {
        cookieState += (ca[i].indexOf(nameEQ) > -1 ? 1 : 0);
    }

    return cookieState > 0;
}

/**
 * sets cookie
 */
function acceptCookies(){
    createCookie("buwiwm_cookie_accept", "true", 1000);
    PF('cookieBarWidget').hide();
}

/**
 * cookie monster!
 */
$(window).load(
    function () {
        if(!readCookie("buwiwm_cookie_accept")){
            PF("cookieBarWidget").show();
        }
    }
);