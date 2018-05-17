/* eslint no-unused-expressions: 0 */
import React, { Component } from 'react';
import { Dialog, Tab, Button, Table, Pagination } from '@icedesign/base';
import axios from 'axios';
import DataBinder from '@icedesign/data-binder';
import IceContainer from '@icedesign/container';

const styles = {
  pagination: {
    textAlign: 'right',
    paddingTop: '26px',
  },
  processing: {
    color: '#5485F7',
  },
  finish: {
    color: '#64D874',
  },
  terminated: {
    color: '#FA7070',
  },
  notstart: {
    color: '#999999',
  },
  dialog: {
    width: '640px',
  },
  dialogContent: {},
  tabContentWrapper: {
    padding: '20px 0 20px 0',
  },
  tabContent: {
    display: 'flex',
    minHeight: '100px',
    fontSize: '20px',
  },
  buttonDialog: {
    display: 'inline-block',
    marginRight: '5px',
  },
};
const TabPane = Tab.TabPane;

const statusComponents = {
  doing: <span style={styles.processing}>进行中</span>,
  done: <span style={styles.finish}>已完成</span>,
  terminated: <span style={styles.terminated}>已终止</span>,
  notstart: <span style={styles.notstart}>未开始</span>,
};


@DataBinder({
  statusData: {
    url: '/services/com.lee.tac.facade.pull.TestcaseStatusPullApi',
    method: 'post',
    data: {
      method: 'getAllStatus',
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
      data: [{
        name: '',
        code: '',
      }],
    },
  },
})
export default class TabDialog extends Component {
  static displayName = 'TabDialog';

  static propTypes = {};

  static defaultProps = {};

  constructor(props) {
    super(props);
    this.queryCache = [{
      requestData: {
        testcaseId: '',
        statusCode: '',
      },
      page: {
        currentPage: 1,
        pageSize: 5,
      },
    }];
    this.state = {
      visible: false,
      dataSource: {
        page: {
        },
      },
    };
  }

  // componentDidMount() {
  //   this.props.updateBindingData('statusData');
  // }

  handleTabChange = (key) => {
    const code = key === 'all' ? '' : key;
    this.restorePage();
    this.queryCache[0].requestData.statusCode = code;
    this.fetchData();
  };

  changePage = (currentPage) => {
    this.queryCache[0].page.currentPage = currentPage;
    this.fetchData();
  };

  restorePage = () => {
    this.queryCache[0].page.currentPage = 1;
  }

  onDialogOk = () => {
    this.hideDialog();
  };
  operRender = (value, index, record) => {
    if (record.status === 'done') {
      return (
        <Button size="small" type="light" component="a" href={record.logLink} target="_blank">
          查看
        </Button>
      );
    }
  };

  showDialog = () => {
    this.props.updateBindingData('statusData');
    this.setState({
      visible: true,
    });
    this.queryCache[0].requestData.testcaseId = this.props.testcaseId;
    this.fetchData();
  };

  fetchData = () => {
    axios
      .post('/services/com.lee.tac.facade.pull.TestcaseLogPullApi', {
        method: 'logListSearch',
        id: '1',
        jsonrpc: '2.0',
        params: this.queryCache,
      })
      .then((response) => {
        this.setState({
          dataSource: response.data.result,
        });
      })
      .catch((error) => {
        console.log(error);
      });
  }

  hideDialog = () => {
    this.setState({
      visible: false,
    });
  };

  renderStatus = (value) => {
    return statusComponents[value];
  };

  render() {
    const { data, page } = this.state.dataSource;
    const { statusData } = this.props.bindingData;
    return (
      <div style={styles.buttonDialog}>
        <Dialog
          className="tab-dialog"
          style={styles.dialog}
          autoFocus={false}
          isFullScreen
          title="执行日志"
          {...this.props}
          onOk={this.onDialogOk}
          onClose={this.hideDialog}
          onCancel={this.hideDialog}
          visible={this.state.visible}
        >
          <div style={styles.dialogContent}>
            <div className="tab-table">
              <IceContainer style={{ padding: '0 20px 20px' }}>
                <Tab onChange={this.handleTabChange}>
                  <TabPane tab="全部" key="all">
                    <Table dataSource={data} hasBorder={false}>
                      <Table.Column title="ID" dataIndex="id" width={50} />
                      <Table.Column title="标题" dataIndex="testcaseName" width={100} />
                      <Table.Column title="开始时间" dataIndex="startTime" width={100} />
                      <Table.Column title="结束时间" dataIndex="endTime" width={100} />
                      <Table.Column
                        title="状态"
                        dataIndex="status"
                        cell={this.renderStatus}
                        width={80}
                      />
                      <Table.Column cell={this.operRender} title="详情" width={80} />
                    </Table>
                  </TabPane>
                  {statusData.data.map((item) => {
                    return (
                      <TabPane tab={item.name} key={item.code}>
                        <Table dataSource={data} hasBorder={false}>
                          <Table.Column title="ID" dataIndex="id" width={50} />
                          <Table.Column title="标题" dataIndex="testcaseName" width={100} />
                          <Table.Column title="开始时间" dataIndex="startTime" width={100} />
                          <Table.Column title="结束时间" dataIndex="endTime" width={100} />
                          <Table.Column
                            title="状态"
                            dataIndex="status"
                            cell={this.renderStatus}
                            width={80}
                          />
                          <Table.Column cell={this.operRender} title="详情" width={80} />
                        </Table>
                      </TabPane>
                    );
                  })}
                </Tab>
                <div style={styles.pagination}>
                  <Pagination
                    current={page != null ? page.currentPage : 1}
                    total={page != null ? page.count : 0}
                    pageSize={page != null ? page.pageSize : 5}
                    onChange={this.changePage}
                  />
                </div>
              </IceContainer>
            </div>
          </div>
        </Dialog>
        <Button size="small" type="light" onClick={this.showDialog}>
          日志
        </Button>
      </div>
    );
  }
}
