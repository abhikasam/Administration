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

    $('thead th label').off('click',null).on('click',function(){
        var column=$(this).data('column')
        $('#sortBy').val(column)
        var direction=$(this).next('i').hasClass('fa-sort-down')
        if(direction){
            $('#orderBy').val('ASCENDING')
        }
        else{
            $('#orderBy').val('DESCENDING')
        }
        $('form').off('submit',null).submit()
    })

})