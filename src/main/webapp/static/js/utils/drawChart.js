function drawPieChart(divId, chartName, legendData, seriesData) {
    var myChart = echarts.init(document.getElementById(divId));
    var option = {
        title: {
            text: chartName,
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            align: 'right',
            left: 'left',
            data: legendData
        },
        series: [
            {
                name: '访问来源',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: seriesData,
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
    myChart.setOption(option);
}

function drawTrendChart(divId, chartName, legendData,xData ,seriesData) {
    var myChart = echarts.init(document.getElementById(divId));
    var option = {
        title: {
            text: chartName,
            x: 'left'
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data: legendData
        },

        grid: {
            left: '6%',
            right: '6%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : xData
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : seriesData
    };
    myChart.setOption(option);
}

