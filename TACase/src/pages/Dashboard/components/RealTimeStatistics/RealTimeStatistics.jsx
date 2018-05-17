import React, { Component } from 'react';
import { Grid } from '@icedesign/base';
import DataBinder from '@icedesign/data-binder';

const { Row, Col } = Grid;

@DataBinder({
  NotStartLog: {
    url: '/services/com.lee.tac.facade.pull.TestcaseLogPullApi',
    method: 'post',
    data: {
      method: 'logListSearch',
      id: '1',
      jsonrpc: '2.0',
      params: [{
        requestData: {
          statusCode: 'notstart',
        },
      }],
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
      page: {
        count: 0,
      },
    },
  },
  DoingLog: {
    url: '/services/com.lee.tac.facade.pull.TestcaseLogPullApi',
    method: 'post',
    data: {
      method: 'logListSearch',
      id: '1',
      jsonrpc: '2.0',
      params: [{
        requestData: {
          statusCode: 'doing',
        },
      }],
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
      page: {
        count: 0,
      },
    },
  },
  YesterDayData: {
    url: '/services/com.lee.tac.facade.pull.StatisticsApi',
    method: 'post',
    data: {
      method: 'getYesterdayData',
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
export default class RealTimeStatistics extends Component {
  static displayName = 'RealTimeStatistics';

  static propTypes = {};

  static defaultProps = {};

  constructor(props) {
    super(props);
    this.state = {};
  }

  componentDidMount() {
    this.props.updateBindingData('DoingLog');
    this.props.updateBindingData('NotStartLog');
    this.props.updateBindingData('YesterDayData');
    this.props.updateBindingData('TotalData');
  }

  render() {
    const { DoingLog, NotStartLog, YesterDayData, TotalData } = this.props.bindingData;
    return (
      <Row wrap gutter="20">
        <Col xxs="24" s="12" l="6">
          <div style={{ ...styles.itemBody, ...styles.green }}>
            <div style={styles.itemTitle}>
              <p style={styles.titleText}>测试集统计</p>
              <span style={styles.tag}>实时</span>
            </div>
            <div style={styles.itemRow}>
              <div style={styles.itemCol}>
                <h2 style={styles.itemNum}>{YesterDayData.data.reportCount}</h2>
                <p style={styles.desc}>昨日新增</p>
              </div>
              <div style={styles.itemCol}>
                <h2 style={styles.itemNum}>{TotalData.data.reportCount}</h2>
                <p style={styles.desc}>总数</p>
              </div>
            </div>
          </div>
        </Col>
        <Col xxs="24" s="12" l="6">
          <div style={{ ...styles.itemBody, ...styles.lightBlue }}>
            <div style={styles.itemTitle}>
              <p style={styles.titleText}>测试集执行统计</p>
              <span style={styles.tag}>实时</span>
            </div>
            <div style={styles.itemRow}>
              <div style={styles.itemCol}>
                <h2 style={styles.itemNum}>{YesterDayData.data.reportLogCount}</h2>
                <p style={styles.desc}>昨日次数</p>
              </div>
              <div style={styles.itemCol}>
                <h2 style={styles.itemNum}>{TotalData.data.reportLogCount}</h2>
                <p style={styles.desc}>总共次数</p>
              </div>
            </div>
          </div>
        </Col>
        <Col xxs="24" s="12" l="6">
          <div style={{ ...styles.itemBody, ...styles.darkBlue }}>
            <div style={styles.itemTitle}>
              <p style={styles.titleText}>浏览统计</p>
              <span style={styles.tag}>实时</span>
            </div>
            <div style={styles.itemRow}>
              <div style={styles.itemCol}>
                <h2 style={styles.itemNum}>{YesterDayData.data.viewCount}</h2>
                <p style={styles.desc}>昨日新增</p>
              </div>
              <div style={styles.itemCol}>
                <h2 style={styles.itemNum}>{TotalData.data.viewCount}</h2>
                <p style={styles.desc}>总数</p>
              </div>
            </div>
          </div>
        </Col>
        <Col xxs="24" s="12" l="6">
          <div style={{ ...styles.itemBody, ...styles.navyBlue }}>
            <div style={styles.itemTitle}>
              <p style={styles.titleText}>执行队列统计</p>
              <span style={styles.tag}>实时</span>
            </div>
            <div style={styles.itemRow}>
              <div style={styles.itemCol}>
                <h2 style={styles.itemNum}>{DoingLog.page ? DoingLog.page.count : 0}</h2>
                <p style={styles.desc}>正在执行数</p>
              </div>
              <div style={styles.itemCol}>
                <h2 style={styles.itemNum}>{NotStartLog.page ? NotStartLog.page.count : 0}</h2>
                <p style={styles.desc}>等候执行数</p>
              </div>
            </div>
          </div>
        </Col>
      </Row>
    );
  }
}

const styles = {
  item: {
    width: '25%',
    padding: '0 10px',
  },
  itemBody: {
    marginBottom: '20px',
    padding: '10px 20px',
    borderRadius: '4px',
    color: '#fff',
    height: '144px',
  },
  itemRow: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  itemTitle: {
    position: 'relative',
  },
  titleText: {
    margin: 0,
    fontSize: '14px',
  },
  tag: {
    position: 'absolute',
    right: 0,
    top: 0,
    padding: '2px 4px',
    borderRadius: '4px',
    fontSize: '12px',
    background: 'rgba(255, 255, 255, 0.3)',
  },
  itemNum: {
    margin: '16px 0',
    fontSize: '32px',
  },
  total: {
    margin: 0,
    fontSize: '12px',
  },
  desc: {
    margin: 0,
    fontSize: '12px',
  },
  green: {
    background: '#31B48D',
  },
  lightBlue: {
    background: '#38A1F2',
  },
  darkBlue: {
    background: '#7538C7',
  },
  navyBlue: {
    background: '#3B67A4',
  },
};
