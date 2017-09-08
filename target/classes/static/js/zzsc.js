//jQuery time
// var rootUrl = "http://120.76.122.187:8888";
var rootUrl = publicDom.config.rootUrl;
var editUrl = rootUrl + "/ap/update";
var countUrl = rootUrl + "/ap/statics";
var addUrl = rootUrl + "/ap/addition";
var dataDirDetailUrl = rootUrl + "/DataDir/getDetail";
var deleteUrl = rootUrl + "/ap/delete";
var searchUrl = rootUrl + "/ap/list";
var inLoadUrl = rootUrl + "/ap/upload";
var outLoadUrl = rootUrl + "/ap/export";
var downLoadUrl = rootUrl + "/ap/download";
var lastStaticsResultUrl = rootUrl + "/ap/lastStaticsResult";
var recogniseUrl = rootUrl + "/ap/recognise";
var overdueUrl = rootUrl + "/ap/overdue";
var page = 1;
var message = {
    "key": {},
    "page": page,
    "rows": 4,
}
var data = JSON.stringify(message);
var current_fs, next_fs, previous_fs; //fieldsets
var left, opacity, scale; //fieldset properties which we will animate
var animating; //flag to prevent quick multi-click glitches
$(function () {
    xiayibu();
    editdatil();
    init(data);
    goTo(0);
    detail();
    search();
    deleteDetail();
    count();
    wangjie();
    rending();
    addition();
    inLoad();
    download();
    outLoad();
    getStatus('#gg_owned_city_industry');
    getStatus('#add_status');
    getTreatment('#add_treatment');
})
function renderDate(timeStamp) {
    var date = new Date(timeStamp);
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    if (month < 10) {
        month = "0" + month;
    }
    if (day < 10) {
        day = "0" + day;
    }
    var dateStr = year + "-" + month + "-" + day;
    return dateStr;
}
function init(data) {
    $('#infoTable').find('tbody').empty();
    var totalPage, eachPage, totalItem;
    // 分页初始化 -显示分页条
    publicDom.getData("post", searchUrl, data, function (data) {
        totalPage = parseInt(data.value.pages);
        perPage = data.value.rows;
        $('#showPerPage').val(perPage);
        $('#CurrentPage').val(0);
        page = 1;
        var currentLink = 0;
        if (totalPage != 1) {
            var navigationHtml = '<nav aria-label="Page navigation">' + '<ul class="pagination">' + '<li><a href="javascript:previous();" aria-label="Previous" class="previousLink">'
                + '<span aria-hidden="true">' + '&laquo;' + '</li></span>'
                + '</a>';
            for (var i = 0; i < totalPage; i++) {
                navigationHtml += '<li><a class="pageLink" href="javascript:goTo(' + currentLink + ')" longdesc = "' + currentLink + '"> ' + (currentLink + 1) + '</a></li>';
                // <a class="pageLink" href="javascript:goTo(0)" longdesc="0">1</a> 第一页
                currentLink++;
            }
            navigationHtml += '<li><a href="javascript:nextPage();" aria-label="Next" class="nextLink">'
                + '<span aria-hidden="true">' + '&raquo;' + '</span></li>'
                + '</a></nav>';
            $('#pageNavbar').html(navigationHtml);//将分页条写入页面
            $('#totalPage').val(totalPage);//保存totalPage
            $('.pageLink:first').addClass('activePage');//为第一页添加 .activePage
        }
        else {
            $('#pageNavbar').empty();
        }
    })
}
// 加载对应页数据
function loadInfo(data) {
    publicDom.getData("post", searchUrl, data, function (data) {
        for (var i = 0; i < data.value.data.length; i++) {// 加载集体荣誉信息
            var date = renderDate(data.value.data[i].additionTime);
            $('#infoTable').find('tbody').append('<tr>'
                + '<td><input type="checkbox" name="order"></td>'
                + '<td>' + data.value.data[i].id + '</td>'
                + '<td>' + data.value.data[i].name + '</td>'
                + '<td>' + data.value.data[i].ownedCityIndustry + '</td>'
                + '<td>' + data.value.data[i].organization + '</td>'
                + '<td>' + data.value.data[i].duty + '</td>'
                + '<td>' + data.value.data[i].status.content + '</td>'
                + '<td>' + data.value.data[i].honoraryTitle + '</td>'
                + '<td>' + date + '</td>'
                + '<td><button class="btn btn-default checkInfo" data-toggle="modal" data-target="#infoModal">' + '详情' + '</button></td>'
                + '<td><button class="btn btn-default editBtnUser"  data-toggle="modal" data-target="#editModal">' + '编辑' + '</button></td>'
                + '</tr>'
            )
        }
    })
}
// 上一页
function previous() {
    var newPage = parseInt($('#CurrentPage').val()) - 1;
    if (newPage >= 0) {
        goTo(newPage);
    }
    else {
        alert("已是最前一页!");
    }
}
// 下一页
function nextPage() {
    var newPage = parseInt($('#CurrentPage').val()) + 1;
    var totalPage = parseInt($('#totalPage').val());
    if (newPage < totalPage) {
        goTo(newPage);
    }
    else {
        alert("已是最后一页!");
    }
}
// 跳转到页
function goTo(newPage) {
    // var showPerPage = parseInt($('#showPerPage').val());
    message.page = newPage + 1; //修改要查询的page值
    $('#infoTable').find('tbody').empty();
    var data = JSON.stringify(message);
    loadInfo(data);
    $('.activePage').removeClass('activePage');
    $('.pageLink[longdesc=' + newPage + ']').addClass('activePage');//为新页面添加 .activePage
    $('#CurrentPage').val(newPage);//修改CurrentPage值
}
function xiayibu() {
    $("#add_addlsry").click(function () {
        var i = $("#add_lsry tbody tr").length;
        i++;
        $("#add_lsry tbody").append('<tr>' +
            '<th scope="row">' + i + '</th>' +
            '<td><input type="text"  placeholder="" /></td>' +
            '<td><input type="text"  placeholder="" /></td>' +
            '<td><input type="text"  placeholder="" /></td>' +
            '</tr>')
    })
    $("#add_deletelsry").click(function () {
        $("#add_lsry tbody tr:last").remove();
    })
    $("#edit_addlsry").click(function () {
        var i = $("#edit_lsry tbody tr").length;
        i++;
        $("#edit_lsry tbody").append('<tr>' +
            '<th scope="row">' + i + '</th>' +
            '<td><input type="text"  placeholder="" /></td>' +
            '<td><input type="text"  placeholder="" /></td>' +
            '<td><input type="text"  placeholder="" /></td>' +
            '</tr>')
    })
    $("#edit_deletelsry").click(function () {
        $("#edit_lsry tbody tr:last").remove();
    })
    $(".next").click(function () {
        if (animating) return false;
        animating = true;
        current_fs = $(this).parent();
        next_fs = $(this).parent().next();
        //activate next step on progressbar using the index of next_fs
        $("#progressbar li").eq($("fieldset").index(next_fs)).addClass("active");
        //show the next fieldset
        next_fs.show();
        //hide the current fieldset with style
        current_fs.animate({opacity: 0}, {
            step: function (now, mx) {
                //as the opacity of current_fs reduces to 0 - stored in "now"
                //1. scale current_fs down to 80%
                scale = 1 - (1 - now) * 0.2;
                //2. bring next_fs from the right(50%)
                left = (now * 50) + "%";
                //3. increase opacity of next_fs to 1 as it moves in
                opacity = 1 - now;
                current_fs.css({'transform': 'scale(' + scale + ')'});
                next_fs.css({'left': left, 'opacity': opacity});
            },
            duration: 800,
            complete: function () {
                current_fs.hide();
                animating = false;
            },
            //this comes from the custom easing plugin
            easing: 'easeInOutBack'
        });
    });
    $(".previous").click(function () {
        if (animating) return false;
        animating = true;
        current_fs = $(this).parent();
        previous_fs = $(this).parent().prev();
        //de-activate current step on progressbar
        $("#progressbar li").eq($("fieldset").index(current_fs)).removeClass("active");
        //show the previous fieldset
        previous_fs.show();
        //hide the current fieldset with style
        current_fs.animate({opacity: 0}, {
            step: function (now, mx) {
                //as the opacity of current_fs reduces to 0 - stored in "now"
                //1. scale previous_fs from 80% to 100%
                scale = 0.8 + (1 - now) * 0.2;
                //2. take current_fs to the right(50%) - from 0%
                left = ((1 - now) * 50) + "%";
                //3. increase opacity of previous_fs to 1 as it moves in
                opacity = 1 - now;
                current_fs.css({'left': left});
                previous_fs.css({'transform': 'scale(' + scale + ')', 'opacity': opacity});
            },
            duration: 800,
            complete: function () {
                current_fs.hide();
                animating = false;
            },
            //this comes from the custom easing plugin
            easing: 'easeInOutBack'
        });
    });
    // $("#add_status").append("<option value=null>未认定</option>")
    var i = 0;
}
function detail() {
    var curPage = $('#CurrentPage').val() + 1;
    var detailContent;
    $('body').on('click', '.checkInfo', function () {
        var getId = $(this).parent().siblings('td:eq(1)').text();
        var message = {
            "key": {
                "id": getId
            },
            "page": curPage,
            "rows": 1
        }
        var data = JSON.stringify(message);
        publicDom.getData("post", searchUrl, data, function (data) {
            $('#info_id').val(data.value.data[0].id);
            $('#info_name').val(data.value.data[0].name);
            $('#info_gender').val(data.value.data[0].gender);
            $('#info_birthday').val(data.value.data[0].birthday != null ? renderDate(data.value.data[0].birthday) : "");
            $('#info_nation').val(data.value.data[0].nation);
            $('#info_identity_card').val(data.value.data[0].identityCard);
            $('#info_cultural_level').val(data.value.data[0].culturalLevel);
            $('#info_political_status').val(data.value.data[0].politicalStatus);
            $('#info_organization').val(data.value.data[0].organization);
            $('#info_duty').val(data.value.data[0].duty);
            $('#info_tel').val(data.value.data[0].tel);
            $('#info_owned_city_industry').val(data.value.data[0].ownedCityIndustry);
            $('#info_family_difficulties_and_employment').val(data.value.data[0].familyDifficultiesAndEmployment);
            $('#info_outstanding_deed').val(data.value.data[0].outstandingDeed);
            $('#info_lsry').find('tbody').children().remove();
            for (var i = 0; i < data.value.data[0].historyTitles.length; i++) {
                $('#info_lsry').find('tbody').append('<tr>'
                    + '<th scope="row">' + (i + 1) + '</th>'
                    + '<td>' + data.value.data[0].historyTitles[i].grantUnit + '</td>'
                    + '<td>' + data.value.data[0].historyTitles[i].name + '</td>'
                    + '<td>' + (data.value.data[0].historyTitles[i].obtainDate != null ? renderDate(data.value.data[0].historyTitles[i].obtainDate) : "") + '</td>'
                    + '</tr>'
                )
            }
            $('#info_status').val(data.value.data[0].status.content);
            $('#info_status_information').val(data.value.data[0].statusInformation);
            $('#info_treatment').val(data.value.data[0].treatment.content);
            $('#info_honorary_title').val(data.value.data[0].honoraryTitle);
            if (data.value.data[0].certificationMaterials != null) {
                $('#info_certification_base').val(data.value.data[0].certificationMaterials.certificationBase);
                $('#info_issuing_unit').val(data.value.data[0].certificationMaterials.issuingUnit);
                $('#info_grant_time').val(data.value.data[0].certificationMaterials.grantTime != null ? renderDate(data.value.data[0].certificationMaterials.grantTime) : "")
                $('#info_grant_unit').val(data.value.data[0].certificationMaterials.grantUnit);
                $('#info_file_name_number').val(data.value.data[0].certificationMaterials.fileNameNumber);
                $('#info_deed_briefing').val(data.value.data[0].certificationMaterials.deedBriefing);
                $('#info_opinion').val(data.value.data[0].certificationMaterials.opinion);
                $('#info_profile_name').val(data.value.data[0].certificationMaterials.profileName);
                $('#info_extreatment').val(data.value.data[0].certificationMaterials.treatment);
            }
            $('#info_physicalCondition').val(data.value.data[0].physicalCondition);
        })
    });
}
function editdatil() {
    var lsrylength;
    var getId;
    var curPage;
    var detailContent;
    $('body').on('click', '.editBtnUser', function () {
        curPage = parseInt($('#CurrentPage').val()) + 1;
        getId = $(this).parent().siblings('td:eq(1)').text();
        var message = {
            "key": {
                "id": getId
            },
            "page": 1,
            "rows": 1
        }
        var data = JSON.stringify(message);
        getTreatment('#edit_treatment');

        publicDom.getData("post", searchUrl, data, function (data) {
            lsrylength = data.value.data[0].historyTitles.length;
            $('#edit_id').val(data.value.data[0].id);
            $('#edit_name').val(data.value.data[0].name);
            $('#edit_gender').val(data.value.data[0].gender);
            $('#edit_birthday').val(data.value.data[0].birthday != null ? renderDate(data.value.data[0].birthday) : "");
            $('#edit_nation').val(data.value.data[0].nation);
            $('#edit_identity_card').val(data.value.data[0].identityCard);
            $('#edit_cultural_level').val(data.value.data[0].culturalLevel);
            $('#edit_political_status').val(data.value.data[0].politicalStatus);
            $('#edit_organization').val(data.value.data[0].organization);
            $('#edit_duty').val(data.value.data[0].duty);
            $('#edit_tel').val(data.value.data[0].tel);
            $('#edit_owned_city_industry').val(data.value.data[0].ownedCityIndustry);
            $('#edit_family_difficulties_and_employment').val(data.value.data[0].familyDifficultiesAndEmployment);
            $('#edit_outstanding_deed').val(data.value.data[0].outstandingDeed);
            $('#edit_lsry').find('tbody').children().remove();
            for (var i = 0; i < lsrylength; i++) {
                $('#edit_lsry').find('tbody').append('<tr>'
                    + '<th scope="row">' + (i + 1) + '</th>'
                    + '<td data-id="' + data.value.data[0].historyTitles[i].id + '">' + data.value.data[0].historyTitles[i].grantUnit + '</td>'
                    + '<td>' + data.value.data[0].historyTitles[i].name + '</td>'
                    + '<td>' + (data.value.data[0].historyTitles[i].obtainDate != null ? renderDate(data.value.data[0].historyTitles[i].obtainDate) : "") + '</td>'
                    + '</tr>'
                )
            }
            $('#edit_status').val(data.value.data[0].status.content);
            $('#edit_status_information').val(data.value.data[0].statusInformation);
            $('#edit_treatment').val(data.value.data[0].treatment.id);
            $('#edit_honorary_title').val(data.value.data[0].honoraryTitle);
            if (data.value.data[0].certificationMaterials != null) {
                $('#edit_certification_base').val(data.value.data[0].certificationMaterials.certificationBase);
                $('#edit_issuing_unit').val(data.value.data[0].certificationMaterials.issuingUnit);
                $('#edit_grant_time').val(data.value.data[0].certificationMaterials.grantTime != null ? renderDate(data.value.data[0].certificationMaterials.grantTime) : "");
                $('#edit_grant_unit').val(data.value.data[0].certificationMaterials.grantUnit);
                $('#edit_file_name_number').val(data.value.data[0].certificationMaterials.fileNameNumber);
                $('#edit_deed_briefing').val(data.value.data[0].certificationMaterials.deedBriefing);
                $('#edit_opinion').val(data.value.data[0].certificationMaterials.opinion);
                $('#edit_profile_name').val(data.value.data[0].certificationMaterials.profileName);
                $('#edit_extreatment').val(data.value.data[0].certificationMaterials.treatment);
                $('#edit_physicalCondition').val(data.value.data[0].physicalCondition);
                $('#edit_profile_name').attr("name", data.value.data[0].certificationMaterials.id);
            }
        })
    })


    $('body').on('click', '#editSave', function () {
        var historyTitles = new Array();
        for (var i = 0; i < lsrylength; i++) {
            var time = $("#edit_lsry tbody tr:eq(" + i + ")").find("td:eq(2)").text();

            // time =  renderDate(time);
            historyTitles[i] = new Object();
            historyTitles[i].apId = getId;
            historyTitles[i].grantUnit = $("#edit_lsry tbody tr:eq(" + i + ")").find("td:eq(0)").text();
            historyTitles[i].id = $("#edit_lsry tbody tr:eq(" + i + ")").find("td:eq(0)").attr("data-id");
            historyTitles[i].name = $("#edit_lsry tbody tr:eq(" + i + ")").find("td:eq(1)").text();
            historyTitles[i].obtainDate = time != null && time != "" ? time : null;
        }
        var edit_name = $("#edit_name").val();
        var edit_gender = $("#edit_gender").val();
        var edit_birthday = $("#edit_birthday").val();
        var edit_nation = $("#edit_nation").val();
        var edit_identity_card = $("#edit_identity_card").val();
        var edit_cultural_level = $("#edit_cultural_level").val();
        var edit_political_status = $("#edit_political_status").val();
        var edit_organization = $("#edit_organization").val();
        var edit_duty = $("#edit_duty").val();
        var edit_tel = $("#edit_tel").val();
        var edit_owned_city_industry = $("#edit_owned_city_industry").val();
        var edit_family_difficulties_and_employment = $("#edit_family_difficulties_and_employment").val();
        var edit_outstanding_deed = $("#edit_outstanding_deed").val();
        var edit_treatment = $("#edit_treatment").val();
        var edit_honorary_title = $("#edit_honorary_title").val();
        var edit_certification_base = $("#edit_certification_base").val();
        var edit_issuing_unit = $("#edit_issuing_unit").val();
        var edit_grant_time = $("#edit_grant_time").val();
        var edit_grant_unit = $("#edit_grant_unit").val();
        var edit_file_name_number = $("#edit_file_name_number").val();
        var edit_deed_briefing = $("#edit_deed_briefing").val();
        var edit_opinion = $("#edit_opinion").val();
        var edit_profile_name = $("#edit_profile_name").val();
        var edit_extreatment = $("#edit_extreatment").val();
        var edit_physicalCondition = $('#edit_physicalCondition').val();
        var edit_certificationMaterials_id = $('#edit_profile_name').attr("name");
        var message = {
            id: getId,
            birthday: edit_birthday,
            certificationMaterials: {
                id: edit_certificationMaterials_id,
                certificationBase: edit_certification_base,
                deedBriefing: edit_deed_briefing,
                fileNameNumber: edit_file_name_number,
                grantTime: edit_grant_time,
                grantUnit: edit_grant_unit,
                issuingUnit: edit_issuing_unit,
                opinion: edit_opinion,
                profileName: edit_profile_name,
                treatment: edit_extreatment,
            },
            culturalLevel: edit_cultural_level,
            duty: edit_duty,
            familyDifficultiesAndEmployment: edit_family_difficulties_and_employment,
            gender: edit_gender,
            historyTitles: historyTitles,
            honoraryTitle: edit_honorary_title,
            identityCard: edit_identity_card,
            name: edit_name,
            nation: edit_nation,
            organization: edit_organization,
            outstandingDeed: edit_outstanding_deed,
            ownedCityIndustry: edit_owned_city_industry,
            physicalCondition: edit_physicalCondition,
            politicalStatus: edit_political_status,
            tel: edit_tel,
            treatment: {
                id: edit_treatment
            }

        }
        var data = JSON.stringify(message);

        publicDom.getData("post", editUrl, data, function (data) {
            if (!data.code) {
                alert(data.value);
                $('#editModal').modal('hide');

                goTo(curPage - 1);
            }
        })
    })
}

function download() {
    $('#downLoad').on('click', function () {
        var ids = "";
        $('#infoTable').find('tbody').find("input[type='checkbox']:checked").each(function () {
            ids += $(this).parent().siblings('td:eq(0)').text() + ",";//拼接获得的id
        });
        console.log(ids);
        window.open(downLoadUrl + "?ids=" + ids, "_self");
    });
}

// 获取劳模状态
function getStatus(element) {
    publicDom.getData("post", dataDirDetailUrl, JSON.stringify({"dataDir": 2}), function (data) {
        if (data.code == 0) {
            var select = $(element);
            select.find("option").remove();
            if (element == "#add_status") {
                for (var i = 0; i < data.value.length; i++) {
                    if (data.value[i].content == "未认定") {
                        select.append("<option value = '" + data.value[i].id + "'>" + data.value[i].content + "</option>");
                        break;
                    }
                }
            } else {
                for (var i = 0; i < data.value.length; i++) {// 加载先进个人状态下拉框
                    select.append("<option value = '" + data.value[i].id + "'>" + data.value[i].content + "</option>");
                }
            }
        } else {
            console.log("加载先进个人状态下拉框失败");
        }
    });

}

// 获取待遇
function getTreatment(element) {
    publicDom.getData("post", dataDirDetailUrl, JSON.stringify({"dataDir": 3}), function (data) {
        if (data.code == 0) {
            var select = $(element);
            select.find("option").remove();
            for (var i = 0; i < data.value.length; i++) {// 加载先进个人状态下拉框
                select.append("<option value = '" + data.value[i].id + "'>" + data.value[i].content + "</option>");
            }
        } else {
            console.log("加载先进个人状态下拉框失败");
        }
    });
}

// $(".submit").click(function(){
// })
function search() {
    // 按单位名称查询
    $('#searchBtn').on('click', function () {
        var page = 1;
        var term = $('#searchTerm').val();
        var inputTerm = $('#inputSearchText').val();
        message.key = {
            "id": inputTerm
        };
        var data = JSON.stringify(message);
        console.log(data);
        init(data);
        loadInfo(data);
    })
}
function deleteDetail() {
    $('#dirDetailDel').on('click', function () {
        var ids = "";
        // 遍历被选中的checkbox
        $('#infoTable').find('tbody').find("input[type='checkbox']:checked").each(function () {
            ids += $(this).parent().siblings('td:eq(0)').text() + ",";//拼接获得的id
        });
        ids = ids.substring(0, ids.length - 1);
        if (ids != "") {
            if (confirm("确定要删除选中的项吗?")) {
                var delete_ids = {
                    "ids": ids,
                }
                var data = JSON.stringify(delete_ids);
                console.log(data);
                publicDom.getData("post", deleteUrl, data, function (data) {
                    if (!data.code) {
                        alert(data.value);
                        init(JSON.stringify(message));
                        goTo(0);// 重新加载明细
                    }
                    else {
                        alert(data.value);
                    }
                })
            }
            else {
            }
        }
        else {
            alert("未有选中项！")
        }
    });
}
function count() {
    $('#igg').on('click', function () {
        publicDom.getData("get", lastStaticsResultUrl, null, function (data) {
            if (!data.code) {
                alert(date.value);
                return;
            } else {
                var bdd = JSON.parse(data.value.resultContext);
                console.log(bdd);
                var historyTitlesName1 = new Array();
                for (var i = 0; i < bdd[0].value.length; i++) {
                    historyTitlesName1[i] = bdd[0].value[i].name;
                }
                var historyTitlesName2 = new Array();
                for (var i = 0; i < bdd[1].value.length; i++) {
                    historyTitlesName2[i] = bdd[1].value[i].name;
                }
                var historyTitlesName3 = new Array();
                for (var i = 0; i < bdd[2].value.length; i++) {
                    historyTitlesName3[i] = bdd[2].value[i].name;
                }
                var myChart1 = echarts.init(document.getElementById('iimain1'));
                // 指定图表的配置项和数据
                option1 = {
                    title: {
                        text: '荣耀称号统计',
                        subtext: '仅供参考',
                        x: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: historyTitlesName1
                    },
                    series: [
                        {
                            name: '- 先进个人',
                            type: 'pie',
                            radius: '55%',
                            center: ['50%', '60%'],
                            data: bdd[0].value,
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };
                // 使用刚指定的配置项和数据显示图表。
                myChart1.setOption(option1);
                var myChart2 = echarts.init(document.getElementById('iimain2'));
                // 指定图表的配置项和数据
                option2 = {
                    title: {
                        text: '地州市统计',
                        subtext: '仅供参考',
                        x: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: historyTitlesName2
                    },
                    series: [
                        {
                            name: '- 先进个人',
                            type: 'pie',
                            radius: '55%',
                            center: ['50%', '60%'],
                            data: bdd[1].value,
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };
                // 使用刚指定的配置项和数据显示图表。
                myChart2.setOption(option2);
                var myChart3 = echarts.init(document.getElementById('iimain3'));
                // 指定图表的配置项和数据
                option3 = {
                    title: {
                        text: '民族统计',
                        subtext: '仅供参考',
                        x: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: historyTitlesName3
                    },
                    series: [
                        {
                            name: '- 先进个人',
                            type: 'pie',
                            radius: '55%',
                            center: ['50%', '60%'],
                            data: bdd[2].value,
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };
                // 使用刚指定的配置项和数据显示图表。
                myChart3.setOption(option3);
            }
        })
        $("#zuijin").css("display", "block");
        $("#xianzai").css("display", "none");
        $('#countBtn').on('click', function () {
            var start = $('#startDate').val();
            var end = $('#endDate').val();
            if (start != "" && end != "" && start < end) {
                console.log(start, end);
                var message = {
                    "beginDate": start,
                    "endDate": end
                }
                var data = JSON.stringify(message);
                publicDom.getData("post", countUrl, data, function (data) {
                    if (!data.code) {
                        $("#zuijin").css("display", "none");
                        $("#xianzai").css("display", "block");
                        // var historyTitles = new Array();
                        // for (var i = 0; i < data.value.length; i++) {
                        // 	historyTitles[i] = new Object();
                        // 	historyTitles[i].value = data.value[i].num;
                        // 	historyTitles[i].name = data.value[i].title;
                        // }
                        var historyTitlesName4 = new Array();
                        for (var i = 0; i < data.value[0].value.length; i++) {
                            historyTitlesName4[i] = data.value[0].value[i].name;
                        }
                        var historyTitlesName5 = new Array();
                        for (var i = 0; i < data.value[1].value.length; i++) {
                            historyTitlesName5[i] = data.value[1].value[i].name;
                        }
                        var historyTitlesName6 = new Array();
                        for (var i = 0; i < data.value[2].value.length; i++) {
                            historyTitlesName6[i] = data.value[2].value[i].name;
                        }
                        // 基于准备好的dom，初始化echarts实例
                        var myChart4 = echarts.init(document.getElementById('iimain4'));
                        // 指定图表的配置项和数据
                        option4 = {
                            title: {
                                text: '荣耀称号统计',
                                subtext: '仅供参考',
                                x: 'center'
                            },
                            tooltip: {
                                trigger: 'item',
                                formatter: "{a} <br/>{b} : {c} ({d}%)"
                            },
                            legend: {
                                orient: 'vertical',
                                left: 'left',
                                data: historyTitlesName4
                            },
                            series: [
                                {
                                    name: '- 先进个人',
                                    type: 'pie',
                                    radius: '55%',
                                    center: ['50%', '60%'],
                                    data: data.value[0].value,
                                    itemStyle: {
                                        emphasis: {
                                            shadowBlur: 10,
                                            shadowOffsetX: 0,
                                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                                        }
                                    }
                                }
                            ]
                        };
                        // 使用刚指定的配置项和数据显示图表。
                        myChart4.setOption(option4);
                        var myChart5 = echarts.init(document.getElementById('iimain5'));
                        // 指定图表的配置项和数据
                        option5 = {
                            title: {
                                text: '地州市统计',
                                subtext: '仅供参考',
                                x: 'center'
                            },
                            tooltip: {
                                trigger: 'item',
                                formatter: "{a} <br/>{b} : {c} ({d}%)"
                            },
                            legend: {
                                orient: 'vertical',
                                left: 'left',
                                data: historyTitlesName5
                            },
                            series: [
                                {
                                    name: '- 先进个人',
                                    type: 'pie',
                                    radius: '55%',
                                    center: ['50%', '60%'],
                                    data: data.value[1].value,
                                    itemStyle: {
                                        emphasis: {
                                            shadowBlur: 10,
                                            shadowOffsetX: 0,
                                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                                        }
                                    }
                                }
                            ]
                        };
                        // 使用刚指定的配置项和数据显示图表。
                        myChart5.setOption(option5);
                        var myChart6 = echarts.init(document.getElementById('iimain6'));
                        // 指定图表的配置项和数据
                        option6 = {
                            title: {
                                text: '民族统计',
                                subtext: '仅供参考',
                                x: 'center'
                            },
                            tooltip: {
                                trigger: 'item',
                                formatter: "{a} <br/>{b} : {c} ({d}%)"
                            },
                            legend: {
                                orient: 'vertical',
                                left: 'left',
                                data: historyTitlesName6
                            },
                            series: [
                                {
                                    name: '- 先进个人',
                                    type: 'pie',
                                    radius: '55%',
                                    center: ['50%', '60%'],
                                    data: data.value[2].value,
                                    itemStyle: {
                                        emphasis: {
                                            shadowBlur: 10,
                                            shadowOffsetX: 0,
                                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                                        }
                                    }
                                }
                            ]
                        };
                        // 使用刚指定的配置项和数据显示图表。
                        myChart6.setOption(option6);
                        if (data.value.length == 0) {
                            alert("该时段未有统计数据！");
                        }
                        else {
                        }
                    }
                    else {
                        alert(data.value);
                    }
                })
            }
            else if (start == "") {
                alert("请选择开始日期！");
            }
            else if (end == "") {
                alert("请选择结束日期！");
            }
            else if (start > end) {
                alert("开始日期不能大于结束日期！")
            }
            else {
            }
        });
    });
}
function wangjie() {


    var curPage;


    $('#mmp').on('click', function () {

        curPage = parseInt($('#CurrentPage').val()) + 1;
        var ids = "";
        // 遍历被选中的checkbox
        $('#infoTable').find('tbody').find("input[type='checkbox']:checked").each(function () {
            ids += $(this).parent().siblings('td:eq(0)').text() + ",";//拼接获得的id
        });
        ids = ids.substring(0, ids.length - 1);
        if (ids != "") {
            if (confirm("确定要变更选中的项为往届吗?")) {
                var overdue_ids = {
                    "ids": ids,
                }
                var data = JSON.stringify(overdue_ids);
                console.log(data);
                publicDom.getData("post", overdueUrl, data, function (data) {
                    if (!data.code) {
                        alert(data.value);

                        goTo(curPage - 1);// 重新加载明细
                    }
                    else {
                        alert(data.value);
                    }
                })
            }
            else {
            }
        }
        else {
            alert("未有选中项！")
        }
    });
}
function rending() {

    var curPage;


    var historyTitles = new Array();
    $('#cnm').on('click', function () {

        curPage = parseInt($('#CurrentPage').val()) + 1;
        // 遍历被选中的checkbox
        var i = 0;
        $('#infoTable').find('tbody').find("input[type='checkbox']:checked").each(function () {
            historyTitles[i] = new Object();
            historyTitles[i].id = $(this).parent().siblings('td:eq(0)').text();
            i++;
        });
    });
    $('#recogniseSave').on('click', function () {
        var gg_owned_city_industry = $("#gg_owned_city_industry").val();
        var gg_status_information = $("#gg_status_information").val();
        for (var i = 0; i < historyTitles.length; i++) {
            historyTitles[i].status = gg_owned_city_industry;
            historyTitles[i].statusInformation = gg_status_information;
        }
        var data = JSON.stringify(historyTitles);
        console.log(data);
        publicDom.getData("post", recogniseUrl, data, function (data) {
            if (!data.code) {
                alert(data.value);

                $('#irecognise').modal('hide');
                goTo(curPage - 1);
            }
        });
    });
}


function addition() {
    $('#submit').bind("click", function () {
        var lsrylength = $("#add_lsry tbody").find("tr").length;
        var historyTitles = new Array();
        for (var i = 0; i < lsrylength; i++) {
            var grantUnit = $("#add_lsry tbody tr:eq(" + i + ")").find("td:eq(0) input").val();
            var name = $("#add_lsry tbody tr:eq(" + i + ")").find("td:eq(1) input").val();
            var obtainDate = $("#add_lsry tbody tr:eq(" + i + ")").find("td:eq(2) input").val();
            if (grantUnit != null && grantUnit != "" && name != null && name != "") {
                historyTitles[i] = new Object();
                historyTitles[i].grantUnit = grantUnit;
                historyTitles[i].name = name;
                historyTitles[i].obtainDate = obtainDate != null && obtainDate != "" ? obtainDate : null;
            }
        }
        console.log(historyTitles);
        var add_name = $("#add_name").val();
        var add_gender = $("#add_gender").val();
        var add_birthday = $("#add_birthday").val();
        var add_nation = $("#add_nation").val();
        var add_identity_card = $("#add_identity_card").val();
        var add_cultural_level = $("#add_cultural_level").val();
        var add_political_status = $("#add_political_status").val();
        var add_organization = $("#add_organization").val();
        var add_duty = $("#add_duty").val();
        var add_tel = $("#add_tel").val();
        var add_owned_city_industry = $("#add_owned_city_industry").val();
        var add_family_difficulties_and_employment = $("#add_family_difficulties_and_employment").val();
        var add_outstanding_deed = $("#add_outstanding_deed").val();
        var add_status = $("#add_status").val();
        var add_status_information = $("#add_status_information").val();
        var add_treatment = $("#add_treatment").val();
        var add_honorary_title = $("#add_honorary_title").val();
        var add_certification_base = $("#add_certification_base").val();
        var add_issuing_unit = $("#add_issuing_unit").val();
        var add_grant_time = $("#add_grant_time").val();
        var add_grant_unit = $("#add_grant_unit").val();
        var add_file_name_number = $("#add_file_name_number").val();
        var add_deed_briefing = $("#add_deed_briefing").val();
        var add_opinion = $("#add_opinion").val();
        var add_profile_name = $("#add_profile_name").val();
        var add_extreatment = $("#add_extreatment").val();
        var add_physicalCondition = $('#add_physicalCondition').val();
        var add_certificationMaterials_id = $('#add_profile_name').attr("name");
        var message = {
            birthday: add_birthday,
            certificationMaterials: {
                id: add_certificationMaterials_id,
                certificationBase: add_certification_base,
                deedBriefing: add_deed_briefing,
                fileNameNumber: add_file_name_number,
                grantTime: add_grant_time,
                grantUnit: add_grant_unit,
                issuingUnit: add_issuing_unit,
                opinion: add_opinion,
                profileName: add_profile_name,
                treatment: add_extreatment,
            },
            culturalLevel: add_cultural_level,
            duty: add_duty,
            familyDifficultiesAndEmployment: add_family_difficulties_and_employment,
            gender: add_gender,
            historyTitles: historyTitles,
            honoraryTitle: add_honorary_title,
            identityCard: add_identity_card,
            name: add_name,
            nation: add_nation,
            organization: add_organization,
            outstandingDeed: add_outstanding_deed,
            ownedCityIndustry: add_owned_city_industry,
            physicalCondition: add_physicalCondition,
            politicalStatus: add_political_status,
            status: {
                id: add_status
            },
            statusInformation: add_status_information,
            tel: add_tel,
            treatment: {
                id: add_treatment
            }
        }
        console.log(message);
        publicDom.getData("post", addUrl, JSON.stringify(message), function (data) {
            if (data.code == 0) {
                alert("添加成功");
            } else {
                alert(data.value);
            }
        });
    });
}


// 数据导出
function outLoad() {
    $('#loadBtn').on('click', function () {
        var start = $('#startLoad').val();
        var end = $('#endLoad').val();
        if (start != "" && end != "" && start < end) {
            window.open(outLoadUrl + "?beginDate=" + start + "&endDate=" + end + "", "_self");
        }
        else if (start == "") {
            alert("请选择起始日期!");
        }
        else if (end == "") {
            alert("请选择结束日期!")
        }
        else if (start > end) {
            alert("起始日期大于结束日期，请重新选择!");
        }
        else {

        }
    });


}


function inLoad() {
    $('#loadIn').on('click', function () {
        $('#fileName').val('');
        $('#fileName').click();
    })
    $('#fileName').unbind().change(function () {
        // var url = "http://182.149.196.33:8888/ac/upload";
        var file = this.files[0];
        var formData = new FormData();
        formData.append("file", file);
        console.log(file)
        if (!(/.\.xls|.\.xlsx/).exec(file.name)) {
            alert("请确保上传.xls或.xlsx文件格式！");
        }
        else {
            $.ajax({
                url: inLoadUrl,
                type: 'POST',
                data: formData,
                async: false,
                cache: false,
                enctype: 'multipart/form-data',
                contentType: false,
                processData: false,
                success: function (data) {
                    console.log(data);
                    if (data.code == 0 && Object.prototype.toString.call(data.value) === "[object String]") {
                        alert("导入失败!" + data.value);
                    } else {
                        $('#loadIn_Hidden').trigger("click");
                        if (data.value.error == 0) {
                            $('#cnmmm').text("导入文件" + file.name + "成功!\n" + "成功导入" + data.value.success + "条记录");
                            $("#error_table").css("display", "none");
                        } else {
                            $("#error_table").css("display", "block");
                            $('#cnmmm').text("导入文件" + file.name + "成功!\n" + "成功导入" + data.value.success + "条记录");
                            $('#mdzz').find('tbody tr').remove();
                            for (var i = 0; i < data.value.data.length; i++) {
                                $('#mdzz').find('tbody').append('<tr>'
                                    + '<td>' + data.value.data[i].name + '</td>'
                                    + '<td>' + data.value.data[i].status.content + '</td>'
                                    + '<td>' + data.value.data[i].treatment.content + '</td>'
                                    + '<td>' + data.value.data[i].ownedCityIndustry + '</td>'
                                    + '<td>' + data.value.data[i].error + '</td>'
                                    + '</tr>');
                            }
                        }
                        var message = {
                            "key": {},
                            "page": 1,
                            "rows": 4,
                        }
                        var data = JSON.stringify(message);
                        init(data);
                        goTo(0);
                    }
                },
                error: function (data) {
                    alert("导入时发生错误，导入失败！")
                }
            });
        }
    })
}