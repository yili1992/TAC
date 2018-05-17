import React, { Component } from 'react';
import IceContainer from '@icedesign/container';
import DataBinder from '@icedesign/data-binder';
import { Grid } from '@icedesign/base';
import './LatestNews.scss';

const { Row, Col } = Grid;

@DataBinder({
  newReports: {
    url: '/services/com.lee.tac.facade.pull.TestcasePullApi',
    method: 'post',
    data: {
      method: 'testCaseListSearch',
      id: '1',
      jsonrpc: '2.0',
      params: [{
        page: {
          currentPage: 1,
          pageSize: 6,
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
        count: 1,
      },
    },
  },
  newExecutes: {
    url: '/services/com.lee.tac.facade.pull.TestcaseLogPullApi',
    method: 'post',
    data: {
      method: 'logListSearch',
      id: '1',
      jsonrpc: '2.0',
      params: [{
        page: {
          currentPage: 1,
          pageSize: 4,
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
        count: 1,
      },
    },
  },
})
export default class LatestNews extends Component {
  static displayName = 'LatestNews';

  static propTypes = {};

  static defaultProps = {};

  constructor(props) {
    super(props);
    this.state = {
    };
  }
  componentDidMount() {
    this.props.updateBindingData('newReports');
    this.props.updateBindingData('newExecutes');
  }

  getText = (status) => {
    if (status === 'doing') return '进行中';
    if (status === 'done') return '已完成';
    if (status === 'terminated') return '已终止';
    if (status === 'notstart') return '未开始';
  }

  render() {
    const { newReports, newExecutes } = this.props.bindingData;
    return (
      <div className="latest-news" style={styles.container}>
        <Row wrap gutter="20">
          <Col xxs="24" s="12" l="12">
            <IceContainer style={styles.cardContainer}>
              <h3 style={styles.cardTitle}>
                最新测试集
              </h3>
              <div style={styles.items}>
                {newReports.data.map((item, index) => {
                  return (
                    <a
                      className="link"
                      key={index}
                      href="#"
                      style={styles.item}
                    >
                      <div style={styles.itemTitle}>{item.name}</div>
                      <div style={styles.itemTime}>{item.createTime}</div>
                    </a>
                  );
                })}
              </div>
            </IceContainer>
          </Col>
          <Col xxs="24" s="12" l="12">
            <IceContainer style={styles.cardContainer}>
              <h3 style={styles.cardTitle}>
                最新执行
              </h3>
              <div style={styles.items}>
                {newExecutes.data.map((item, index) => {
                  return (
                    <a
                      className="link"
                      key={index}
                      href="#"
                      style={styles.item}
                    >
                      <div style={styles.itemComment}>
                        <div style={styles.commentTitle}>{item.testcaseName} - ID:{item.id}</div>
                        <div style={styles.commentTime}>{item.startTime}</div>
                      </div>
                      <div style={styles.commentNum}>{this.getText(item.status)}</div>
                    </a>
                  );
                })}
              </div>
            </IceContainer>
          </Col>
        </Row>
      </div>
    );
  }
}

const styles = {
  cardContainer: {
    height: '286px',
    overflowY: 'auto',
  },
  cardTitle: {
    position: 'relative',
    margin: '0 0 10px',
    fontSize: '16px',
    fontWeight: 'bold',
    color: '#333',
  },
  more: {
    position: 'absolute',
    right: 0,
    fontSize: '12px',
    color: '#666',
  },
  item: {
    position: 'relative',
    display: 'block',
  },
  itemTime: {
    position: 'absolute',
    right: 0,
    top: 6,
    fontSize: '12px',
  },
  itemTitle: {
    height: '34px',
    lineHeight: '34px',
    fontSize: '13px',
  },
  itemComment: {
    position: 'relative',
    display: 'flex',
    flexDirection: 'column',
    marginBottom: '10px',
  },
  commentTitle: {
    height: '28px',
    lineHeight: '28px',
    fontSize: '13px',
  },
  commentTime: {
    fontSize: '12px',
  },
  commentNum: {
    position: 'absolute',
    right: 0,
    top: 6,
    width: '36',
    height: '24px',
    lineHeight: '24px',
    fontSize: '12px',
    textAlign: 'center',
    borderRadius: '50px',
  },
  processing: {
    color: '#5485F7',
  },
  finish: {
    color: '#64D874',
  },
  terminated: {
    color: '#999999',
  },
  pass: {
    color: '#FA7070',
  },
};
