import React, { Component } from 'react';
import { Grid } from '@icedesign/base';
import IceContainer from '@icedesign/container';
import DataBinder from '@icedesign/data-binder';
import { Chart, Axis, Geom, Tooltip, G2 } from 'bizcharts';
import './DataStatistics.scss';

const { Row, Col } = Grid;
G2.track(false);

@DataBinder({
  RecentlyYearExecuteData: {
    url: '/services/com.lee.tac.facade.pull.StatisticsApi',
    method: 'post',
    data: {
      method: 'getRecentlyYearExecuteData',
      id: '1',
      jsonrpc: '2.0',
    },
    responseFormatter: (responseHandler, res, originResponse) => {
      // 做一些数据转换
      const newRes = {
        data: res.result,
        status: res.code,
      };
      // 回传给处理函数
      // 不做回传处理会导致数据更新逻辑中断
      responseHandler(newRes, originResponse);
    },
    defaultBindingData: {
      data: [],
    },
  },
  TodayData: {
    url: '/services/com.lee.tac.facade.pull.StatisticsApi',
    method: 'post',
    data: {
      method: 'getTodayData',
      id: '1',
      jsonrpc: '2.0',
    },
    responseFormatter: (responseHandler, res, originResponse) => {
      // 做一些数据转换
      const newRes = {
        data: res.result,
        status: res.code,
      };
      // 回传给处理函数
      // 不做回传处理会导致数据更新逻辑中断
      responseHandler(newRes, originResponse);
    },
    defaultBindingData: {
      data: {
        reportCount: 0,
        reportLogCount: 0,
        projectCount: 0,
        viewCount: 0,
      },
    },
  },
  SevenDayData: {
    url: '/services/com.lee.tac.facade.pull.StatisticsApi',
    method: 'post',
    data: {
      method: 'getSevenDayData',
      id: '1',
      jsonrpc: '2.0',
    },
    responseFormatter: (responseHandler, res, originResponse) => {
      // 做一些数据转换
      const newRes = {
        data: res.result,
        status: res.code,
      };
      // 回传给处理函数
      // 不做回传处理会导致数据更新逻辑中断
      responseHandler(newRes, originResponse);
    },
    defaultBindingData: {
      data: {
        reportCount: 0,
        reportLogCount: 0,
        projectCount: 0,
        viewCount: 0,
      },
    },
  },
  TotalData: {
    url: '/services/com.lee.tac.facade.pull.StatisticsApi',
    method: 'post',
    data: {
      method: 'getTotalData',
      id: '1',
      jsonrpc: '2.0',
    },
    responseFormatter: (responseHandler, res, originResponse) => {
      // 做一些数据转换
      const newRes = {
        data: res.result,
        status: res.code,
      };
      // 回传给处理函数
      // 不做回传处理会导致数据更新逻辑中断
      responseHandler(newRes, originResponse);
    },
    defaultBindingData: {
      data: {
        reportCount: 0,
        reportLogCount: 0,
        projectCount: 0,
        viewCount: 0,
      },
    },
  },
})
export default class DataStatistics extends Component {
  static displayName = 'DataStatistics';

  static propTypes = {};

  static defaultProps = {};

  constructor(props) {
    super(props);
    this.dataSource = {
      statisticData: [
        {
          name: '今日浏览',
          value: 0,
          img: {
            width: 35,
            height: 32,
            url: 'https://img.alicdn.com/tfs/TB1fTidceuSBuNjy1XcXXcYjFXa-70-64.png',
          },
        },
        {
          name: '今日执行',
          value: 0,
          img: {
            width: 30,
            height: 31,
            url: 'https://img.alicdn.com/tfs/TB1fnidceuSBuNjy1XcXXcYjFXa-60-62.png',
          },
        },
        {
          name: '七日浏览',
          value: 0,
          img: {
            width: 28,
            height: 27,
            url: 'https://img.alicdn.com/tfs/TB1gDidceuSBuNjy1XcXXcYjFXa-56-54.png',
          },
        },
        {
          name: '七日执行',
          value: 0,
          img: {
            width: 28,
            height: 26,
            url: 'https://img.alicdn.com/tfs/TB1hDidceuSBuNjy1XcXXcYjFXa-56-52.png',
          },
        },
        {
          name: '七日浏览新增',
          value: 0,
          img: {
            width: 35,
            height: 32,
            url: 'https://img.alicdn.com/tfs/TB11FFTcgmTBuNjy1XbXXaMrVXa-70-64.png',
          },
        },
        {
          name: '七日执行新增',
          value: 0,
          img: {
            width: 28,
            height: 28,
            url: 'https://img.alicdn.com/tfs/TB1h_1jcamWBuNjy1XaXXXCbXXa-56-56.png',
          },
        },
      ],
    };
    this.state = {};
  }

  componentDidMount() {
    this.props.updateBindingData('RecentlyYearExecuteData');
    this.props.updateBindingData('TodayData');
    this.props.updateBindingData('SevenDayData');
    this.props.updateBindingData('TotalData');
  }

  render() {
    const cols = {
      count: { tickInterval: 20 },
    };
    const { RecentlyYearExecuteData } = this.props.bindingData;

    this.dataSource.statisticData[0].value = this.props.bindingData.TodayData.data.viewCount;
    this.dataSource.statisticData[1].value = this.props.bindingData.TodayData.data.reportLogCount;
    this.dataSource.statisticData[2].value = this.props.bindingData.SevenDayData.data.viewCount;
    this.dataSource.statisticData[3].value = this.props.bindingData.SevenDayData.data.reportLogCount;
    const sevenViewCount = this.props.bindingData.SevenDayData.data.viewCount;
    const sevenReportLogCount = this.props.bindingData.SevenDayData.data.reportLogCount;
    let beforeSevenViewCount = this.props.bindingData.TotalData.data.viewCount - sevenViewCount;
    let beforeSevenReportLogCount = this.props.bindingData.TotalData.data.reportLogCount - sevenReportLogCount;
    beforeSevenViewCount = beforeSevenViewCount === 0 ? 1 : beforeSevenViewCount;
    beforeSevenReportLogCount = beforeSevenReportLogCount === 0 ? 1 : beforeSevenReportLogCount;
    this.dataSource.statisticData[4].value = `${Math.trunc((sevenViewCount / beforeSevenViewCount) * 100)}%`;
    this.dataSource.statisticData[5].value = `${Math.trunc((sevenReportLogCount / beforeSevenReportLogCount) * 100)}%`;
    return (
      <div className="data-statistics">
        <IceContainer>
          <h4 style={styles.title}>执行活跃趋势</h4>
          <Row wrap>
            <Col xxs="24" l="16">
              <Chart
                height={300}
                padding={[40, 10, 40, 35]}
                data={RecentlyYearExecuteData.data}
                scale={cols}
                forceFit
              >
                <Axis name="month" />
                <Axis name="value" />
                <Tooltip crosshairs={{ type: 'y' }} />
                <Geom type="interval" position="month*count" />
              </Chart>
            </Col>
            <Col xxs="24" l="8">
              <ul style={styles.items}>
                {this.dataSource.statisticData.map((item, index) => {
                  return (
                    <li key={index} className="item-box" style={styles.itemBox}>
                      <div style={styles.itemIcon}>
                        <img
                          src={item.img.url}
                          style={{
                            width: item.img.width,
                            height: item.img.height,
                          }}
                          alt=""
                        />
                      </div>
                      <div style={styles.itemText}>
                        <div style={styles.name}>{item.name}</div>
                        <div style={styles.value}>{item.value}</div>
                      </div>
                    </li>
                  );
                })}
              </ul>
            </Col>
          </Row>
        </IceContainer>
      </div>
    );
  }
}

const styles = {
  container: {
    width: '100%',
  },
  title: {
    margin: 0,
    fontSize: '16px',
    paddingBottom: '15px',
    fontWeight: 'bold',
    color: '#333',
    borderBottom: '1px solid #eee',
  },
  items: {
    display: 'flex',
    flexDeriction: 'row',
    flexWrap: 'wrap',
    marginLeft: '30px',
  },
  itemBox: {
    display: 'flex',
    flexDirection: 'row',
    width: '50%',
    marginTop: '50px',
    alignItems: 'center',
  },
  itemIcon: {
    marginRight: '10px',
  },
  icon: {
    color: '#3FA1FF',
  },
  value: {
    color: '#1F82FF',
    fontSize: '20px',
  },
  name: {
    fontSize: '12px',
  },
};
