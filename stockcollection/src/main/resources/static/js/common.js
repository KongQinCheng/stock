//paraName 等找参数的名称
//调用方法：getUrlParam("id");
function getUrlParam(paraName) {
    var url = document.location.toString();
    var arrObj = url.split("?");

    if (arrObj.length > 1) {
        var arrPara = arrObj[1].split("&");
        var arr;

        for (var i = 0; i < arrPara.length; i++) {
            arr = arrPara[i].split("=");

            if (arr != null && arr[0] == paraName) {
                return arr[1];
            }
        }
        return "";
    }
    else {
        return "";
    }
}


function toDecimal2(x) {
    var f = parseFloat(x);
    if (isNaN(f)) {
        return false;
    }
    var f = Math.round(x*100)/100;
    var s = f.toString();
    var rs = s.indexOf('.');
    if (rs < 0) {
        rs = s.length;
        s += '.';
    }
    while (s.length <= rs + 2) {
        s += '0';
    }
    return s;
}



function toDecimal4(x) {
    var f = parseFloat(x);
    if (isNaN(f)) {
        return false;
    }
    var f = Math.round(x*10000)/10000;
    var s = f.toString();
    var rs = s.indexOf('.');
    if (rs < 0) {
        rs = s.length;
        s += '.';
    }
    while (s.length <= rs + 4) {
        s += '0';
    }
    return s;
}


function getHtml(title, ownCount, ownCountAll ,allCount,allCountAll) {
    var ownpersent = 0;
    var allpersent = 0;
    if (ownCountAll != 0) {
        ownpersent = ownCount / ownCountAll * 100;
        ownpersent=toDecimal2(ownpersent)
    }

    if (ownCountAll != 0) {
        allpersent = ownCount / ownCountAll * 100;
        allpersent=toDecimal2(allpersent)
    }

    var html = ""
    html += "<tr>"
    html += "<td>" + title + "</td>"

    html += "<td>"
    html += "<div style='width: 100%;background-color: beige'>"
    html += "<div style='width: " + ownpersent + "%;background-color: #eea236'>"
    html += ownpersent
    html += "</div>"
    html += "</div>"
    html += "</td>"

    html += "<td>"

    html += "<div style='width: 100%;background-color: beige'>"
    html += "<div style='width: " + allpersent + "%;background-color: #eea236'>"
    html += allpersent
    html += "</div>"
    html += "</div>"


    html += "</td>"
    html += "<td></td>"
    html += "</tr>"

    return html;
}
