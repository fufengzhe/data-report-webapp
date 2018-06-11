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
function drawMapChart(divId, dataLocation, title, minData, maxData, seriesData) {
    var myChart = echarts.init(document.getElementById(divId));
    $.get(dataLocation, function (chinaJson) {
        echarts.registerMap('USA', chinaJson, {});
        option = {
            title: {
                text: title,
//                    subtext: 'Data from www.census.gov',
//                    sublink: 'http://www.census.gov/popest/data/datasets.html',
                left: 'center'
            },
            tooltip: {
                trigger: 'item',
                showDelay: 0,
                transitionDuration: 0.2,
                formatter: function (params) {
                    var value = (params.value + '').split('.');
                    value = value[0].replace(/(\d{1,3})(?=(?:\d{3})+(?!\d))/g, '$1,');
                    return params.seriesName + '<br/>' + params.name + ': ' + value;
                }
            },
            visualMap: {
                left: 'right',
                min: minData,
                max: maxData,
                inRange: {
                    color: ['#313695', '#4575b4', '#74add1', '#abd9e9', '#e0f3f8', '#ffffbf', '#fee090', '#fdae61', '#f46d43', '#d73027', '#a50026'],
                },
                text: ['高', '低'],           // 文本，默认为数值文本
                calculable: true
            },
            toolbox: {
                show: true,
                //orient: 'vertical',
                left: 'left',
                top: 'top',
                feature: {
                    dataView: {readOnly: false},
                    restore: {},
                    saveAsImage: {}
                }
            },
            series: [
                {
                    name: '数量',
                    type: 'map',
                    roam: true,
                    map: 'USA',
                    itemStyle: {
                        emphasis: {label: {show: true}}
                    },
                    // 文本位置修正
                    textFixed: {
                        Alaska: [20, -20]
                    },
                    data: seriesData
                }
            ]
        };

        myChart.setOption(option);
    });
}
function drawBarChart(divId, chartName, xAxisName, xAxisData, yAxisName, seriesData) {
    var myChart = echarts.init(document.getElementById(divId));
    var option = {
        title: {
            text: chartName,
            x: 'center'
        },
        color: ['#3398DB'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                name: xAxisName,
                data: xAxisData,
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis: [
            {
                name: yAxisName,
                type: 'value'
            }
        ],
        series: [
            {
                name: '活跃人数',
                type: 'bar',
                barWidth: '60%',
                data: seriesData
            }
        ]
    };
    myChart.setOption(option);
}
function drawGraphChart(divId, chartName, formatterFunction, seriesData, linksData) {
    var myChart = echarts.init(document.getElementById(divId));
    var option = {
        title: {
            text: chartName,
            x: 'center'
        },
        tooltip: {
            formatter: formatterFunction,
        },
        animationDurationUpdate: 1500,
        animationEasingUpdate: 'quinticInOut',
        series: [
            {
                type: 'graph',
                layout: 'force',
                force: {
                    // initLayout: 'circular',
                    gravity: 0.05,
                    repulsion: 500,
                    edgeLength: [400, 600]
                },
                symbolSize: 50,
                draggable: true,
                roam: true,
                label: {
                    normal: {
                        show: true
                    }
                },
                edgeSymbol: ['circle', 'arrow'],
                edgeSymbolSize: [0, 10],
                edgeLabel: {
                    normal: {
                        textStyle: {
                            fontSize: 15
                        }
                    }
                },
                data: seriesData,
                links: linksData,
                lineStyle: {
                    normal: {
                        opacity: 1,
                    }
                }
            }
        ]

    };
    myChart.setOption(option);
}
function drawGaugeChart(divId, chartName, seriesData) {
    var myChart = echarts.init(document.getElementById(divId));
    option = {
        title: {
            text: chartName,
            x: 'center'
        },
        tooltip: {
            formatter: "{a} <br/>{b} : {c}%"
        },
        toolbox: {
            feature: {
                restore: {},
                saveAsImage: {}
            }
        },
        series: [
            {
                name: '业务指标',
                type: 'gauge',
                detail: {formatter: '{value}%'},
                data: seriesData,
            }
        ]
    };
    myChart.setOption(option);
}

