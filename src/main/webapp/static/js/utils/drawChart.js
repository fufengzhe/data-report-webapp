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

function drawTrendChart(divId, chartName, legendData, xData, seriesData) {
    var myChart = echarts.init(document.getElementById(divId));
    var option = {
        title: {
            text: chartName,
            x: 'left'
        },
        tooltip: {
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
        xAxis: [
            {
                type: 'category',
                boundaryGap: false,
                data: xData
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: seriesData
    };
    myChart.setOption(option);
}


function drawScatterChart(divId, chartName, legendData, tooltipFormatter, xAxisName, yAxisName, series) {
    var myChart = echarts.init(document.getElementById(divId));
    var option = {
        backgroundColor: new echarts.graphic.RadialGradient(0.3, 0.3, 0.8, [{
            offset: 0,
            color: '#f7f8fa'
        }, {
            offset: 1,
            color: '#cdd0d5'
        }]),
        title: {
            text: chartName
        },
        legend: {
            right: 30,
            data: legendData
        },
        tooltip: {
            trigger: 'item',
            showDelay: 0,
            formatter: tooltipFormatter
        },
        xAxis: {
            splitLine: {
                lineStyle: {
                    type: 'dashed'
                }
            },
            name: xAxisName
        },
        yAxis: {
            splitLine: {
                lineStyle: {
                    type: 'dashed'
                }
            },
            scale: true,
            name: yAxisName
        },
        series: series
    };
    myChart.setOption(option);
}