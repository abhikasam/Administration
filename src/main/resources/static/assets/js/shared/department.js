function changePageSize(event){
    console.log('this time fuck me soo hard.')
    $('#pageSize').val(event.target.value)
    $('form').submit()
}

$(document).ready(function(){
    $('.page-item .page-link').off('click',null).on('click',function(){
        $('#pageNumber').val($(this).data('page'))
        $('form').off('submit',null).submit()
    })

    $('#updateDepartmentFilters').off('click',null).on('click',function(){
        $('form').off('submit',null).submit()
    })

})