function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}
//Vue
var data;
var url = "/projecttree";
if (GetQueryString("all")!==null) {
    url = "/projecttree/all";
}
$.ajax({
    url: url,
    async: false,
    dataType: "json",
    success: function (d) {
        data = d;
    }
});
new Vue({
    el: '#app',
    data() {
        return {
            items: data
        }
    }
});