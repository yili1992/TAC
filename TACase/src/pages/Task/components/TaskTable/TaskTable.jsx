import React, { Component } from 'react';
import IceContainer from '@icedesign/container';
import { Pagination, Feedback, Loading } from '@icedesign/base';
import IceLabel from '@icedesign/label';
import axios from 'axios';
import DataBinder from '@icedesign/data-binder';
import CustomTable from './components/CustomTable';
import EditDialog from './components/EditDialog';
import AddDialog from './components/AddDialog';
import DeleteBalloon from './components/DeleteBalloon';
import ConfirmBalloon from './components/ConfirmBalloon';

const Toast = Feedback.toast;

@DataBinder({
  testcaseData: {
    url: '/services/com.lee.tac.facade.pull.TestcasePullApi',
    method: 'post',
    data: {
      method: 'testCaseListSearch',
      id: '1',
      jsonrpc: '2.0',
      params: [{
        requestData: {
        },
        page: {
          currentPage: 1,
          pageSize: 100,
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
export default class TaskTable extends Component {
  static displayName = 'TaskTable';

  static propTypes = {};

  static defaultProps = {};

  constructor(props) {
    super(props);
    this.queryCache = [{
      requestData: {
      },
      page: {
        currentPage: 1,
        pageSize: 7,
      },
    }];
    this.state = {
      visible: false,
      testcase: [],
      tableData: {
        page: {},
      },
    };
    this.columns = [
      {
        title: 'ID',
        dataIndex: 'id',
        key: 'id',
        width: 30,
      },
      {
        title: '测试集名称',
        dataIndex: 'testcaseName',
        key: 'testcaseName',
        width: 90,
      },
      {
        title: '测试集Id',
        dataIndex: 'testcaseId',
        key: 'testcaseId',
        width: 50,
      },
      {
        title: '负责人',
        dataIndex: 'author',
        key: 'author',
        width: 50,
      },
      {
        title: 'Cron',
        dataIndex: 'cron',
        key: 'cron',
        width: 100,
      },
      {
        title: '状态',
        key: 'enable',
        width: 50,
        render: (value, index, record) => {
          return (
            <IceLabel status={record.enable === true ? 'success' : 'default'}>{record.enable === true ? '启动' : '暂停'}</IceLabel>
          );
        },
      },
      {
        title: '操作',
        key: 'action',
        width: 150,
        render: (value, index, record) => {
          return (
            <span>
              <ConfirmBalloon
                handleExecute={() => this.setTask(index, record, false)}
                btnName="暂停"
              />
              <ConfirmBalloon
                handleExecute={() => this.setTask(index, record, true)}
                btnName="启动"
              />
              <EditDialog
                testcase={this.state.testcase}
                index={index}
                record={record}
                getFormValues={this.updateTask}
              />
              <DeleteBalloon
                handleRemove={() => this.handleRemove(value, index, record)}
              />
            </span>
          );
        },
      },
    ];
  }

  componentDidMount() {
    this.queryCache[0].page.currentPage = 1;
    this.props.updateBindingData('testcaseData', {},
      () => {
        this.setState({
          testcase: this.props.bindingData.testcaseData.data,
        });
      });
    this.fetchData();
  }

  fetchData = () => {
    axios
      .post('/services/com.lee.tac.facade.pull.CronTaskPullApi', {
        method: 'getTasks',
        id: '1',
        jsonrpc: '2.0',
        params: this.queryCache,
      })
      .then((response) => {
        this.setState({
          tableData: response.data.result,
        });
      })
      .catch((error) => {
        console.log(error);
      });
  };

  setTask = (dataIndex, record, status) => {
    const { tableData } = this.state;
    record.enable = status;
    tableData.data[dataIndex] = record;
    this.setState({
      visible: true,
    });
    if (this.status) {
      axios
        .post('/scheduler/resumejob', {
          jobName: record.testcaseId,
          taskId: record.id,
        })
        .then((response) => {
          if (response.data.code === '00000') {
            Toast.success(response.data.msg);
            this.setState({
              tableData,
            });
          } else {
            Toast.error(response.data.msg);
            console.log(response.data);
          }
          this.setState({
            visible: false,
          });
        })
        .catch((error) => {
          console.log(error);
          this.setState({
            visible: false,
          });
        });
    } else {
      axios
        .post('/scheduler/pausejob', {
          jobName: record.testcaseId,
          taskId: record.id,
        })
        .then((response) => {
          if (response.data.code === '00000') {
            Toast.success(response.data.msg);
            this.setState({
              tableData,
            });
          } else {
            Toast.error(response.data.msg);
            console.log(response.data);
          }
          this.setState({
            visible: false,
          });
        })
        .catch((error) => {
          console.log(error);
          this.setState({
            visible: false,
          });
        });
    }
  };

  changePage = (currentPage) => {
    this.queryCache[0].page.currentPage = currentPage;
    this.fetchData();
  };

  restorePage = () => {
    this.queryCache[0].page.currentPage = 1;
  }

  addFormValues = (values) => {
    this.setState({
      visible: true,
    });
    axios
      .post('/scheduler/addjob', {
        jobAuthor: values.author,
        cronExpression: values.cron,
        testCase: values.testcase,
      })
      .then((response) => {
        if (response.data.code === '00000') {
          Toast.success(response.data.msg);
          this.fetchData();
        } else {
          Toast.error(response.data.msg);
          console.log(response.data);
        }
        this.setState({
          visible: false,
        });
      })
      .catch((error) => {
        console.log(error);
        this.setState({
          visible: false,
        });
      });
  };

  updateTask = (dataIndex, values) => {
    const { tableData } = this.state;
    tableData.data[dataIndex] = values;
    this.setState({
      visible: true,
    });
    axios
      .post('/scheduler/reschedulejob', {
        jobAuthor: values.author,
        cronExpression: values.cron,
        jobName: values.testcaseId,
        taskId: values.id,
      })
      .then((response) => {
        if (response.data.result.code === '00000') {
          Toast.success(response.data.result.msg);
          this.setState({
            tableData,
          });
        } else {
          Toast.error(response.data.result.msg);
          console.log(response.data);
        }
        this.setState({
          visible: false,
        });
      })
      .catch((error) => {
        console.log(error);
        this.setState({
          visible: false,
        });
      });
  };

  handleRemove = (value, index, record) => {
    const { tableData } = this.state;
    tableData.data.splice(index, 1);
    axios
      .post('/scheduler/deletejob', {
        jobName: record.testcaseId,
        taskId: record.id,
      })
      .then((response) => {
        if (response.data.code === '00000') {
          Toast.success(response.data.msg);
          this.setState({
            tableData,
          });
        } else {
          Toast.error(response.data.msg);
          console.log(response.data);
        }
      })
      .catch((error) => {
        console.log(error);
      });
  };

  render() {
    const { tableData } = this.state;
    return (
      <div className="tab-table">
        <IceContainer style={{ padding: '0 20px 20px' }}>
          <Loading
            style={{ display: 'block' }}
            visible={this.state.visible}
            shape="flower"
          >
            <AddDialog
              testcase={this.state.testcase}
              addFormValues={this.addFormValues}
            />
            <CustomTable
              dataSource={tableData.data}
              columns={this.columns}
              hasBorder={false}
            />
          </Loading>
          <div style={styles.pagination}>
            <Pagination
              current={tableData.page != null ? tableData.page.currentPage : 1}
              total={tableData.page != null ? tableData.page.count : 0}
              pageSize={tableData.page != null ? tableData.page.pageSize : 7}
              onChange={this.changePage}
            />
          </div>
        </IceContainer>
      </div>
    );
  }
}
const styles = {
  search: {
    float: 'right',
  },
  pagination: {
    textAlign: 'right',
    paddingTop: '26px',
  },
};
