function toggleAbstract(header_id, abstract_id) {
    if ($("#" + abstract_id).is(":visible")) {
        $("#" + header_id).html("<h5 style=\"margin:0\">Abstract [+]</h5>");
    } else {
        $("#" + header_id).html("<h5 style=\"margin:0\">Abstract [-]</h5>");
    }
    $("#" + abstract_id).toggle();
}
