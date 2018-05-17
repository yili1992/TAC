import React, { Component } from 'react';
import IceContainer from '@icedesign/container';
import { Tab, Button, Pagination, Search, Feedback, Loading } from '@icedesign/base';
import axios from 'axios';
import DataBinder from '@icedesign/data-binder';
import CustomTable from './components/CustomTable';
import EditDialog from './components/EditDialog';
import DeleteBalloon from './components/DeleteBalloon';
import ConfirmBalloon from './components/ConfirmBalloon';
import TabDialog from './../TabDialog/TabDialog';

const TabPane = Tab.TabPane;
const Toast = Feedback.toast;

@DataBinder({
  projectData: {
    url: '/services/com.lee.tac.facade.pull.ProjectPullApi',
    method: 'post',
    data: {
      method: 'getProject',
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
      page: {
        count: 1,
      },
    },
  },
})
export default class TabTable extends Component {
  static displayName = 'TabTable';

  static propTypes = {};

  static defaultProps = {};

  constructor(props) {
    super(props);
    this.queryCache = [{
      requestData: {
        name: '',
        projectName: '',
      },
      page: {
        currentPage: 1,
        pageSize: 7,
      },
    }];
    this.state = {
      visible: false,
      value: '',
      projects: [],
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
        title: '项目',
        dataIndex: 'projectName',
        key: 'projectName',
        width: 90,
      },
      {
        title: '标题',
        dataIndex: 'name',
        key: 'name',
        width: 100,
      },
      {
        title: '作者',
        dataIndex: 'author',
        key: 'author',
        width: 50,
      },
      {
        title: '创建时间',
        dataIndex: 'createTime',
        key: 'createTime',
        width: 130,
      },
      {
        title: '最新执行时间',
        dataIndex: 'lastExcuteTime',
        key: 'lastExcuteTime',
        width: 130,
      },
      {
        title: '操作',
        key: 'action',
        width: 150,
        render: (value, index, record) => {
          return (
            <span>
              <ConfirmBalloon
                handleExecute={() => this.ExecuteCase(index, record)}
                btnName={record.num === 0 ? '执行' : '进行中'}
                caseId={record.id}
              />
              <EditDialog
                projects={this.state.projects}
                index={index}
                record={record}
                getFormValues={this.getFormValues}
              />
              <DeleteBalloon
                handleRemove={() => this.handleRemove(value, index, record)}
              />
            </span>
          );
        },
      },
      {
        title: '执行日志',
        key: 'log',
        width: 100,
        render: (value, index, record) => {
          return (
            <TabDialog testcaseId={record.id} />
          );
        },
      },
    ];
  }

  componentDidMount() {
    this.queryCache[0].page.currentPage = 1;
    this.props.updateBindingData('projectData', {},
      () => {
        this.setState({
          projects: this.props.bindingData.projectData.data,
        });
      });
    this.fetchData();
  }

  fetchData = () => {
    axios
      .post('/services/com.lee.tac.facade.pull.TestcasePullApi', {
        method: 'testCaseListSearch',
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

  ExecuteCase = (index, record) => {
    if (record.num > 0) {
      Toast.prompt('测试集正在执行');
    } else {
      this.setState({
        visible: true,
      });
      axios
        // .get(`//127.0.0.1:8088/testcase/${record.id}`) // 测试时候使用
        .get(`/testcase/${record.id}`) // 构建发布使用
        .then((response) => {
          if (response.data.code === '00000') {
            Toast.success(response.data.msg);
            const { tableData } = this.state;
            tableData.data[index].lastExcuteTime = response.data.lastExcuteTime;
            tableData.data[index].num = 1;
            this.setState({
              tableData,
            });
          } else {
            Toast.prompt(response.data.msg);
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
  }

  changePage = (currentPage) => {
    this.queryCache[0].page.currentPage = currentPage;
    this.fetchData();
  };

  restorePage = () => {
    this.queryCache[0].page.currentPage = 1;
  }

  onSearch = (value) => {
    this.queryCache[0].requestData.projectName = value.filterValue;
    this.queryCache[0].requestData.name = value.key;
    this.restorePage();
    this.fetchData();
  }

  getFormValues = (dataIndex, values) => {
    const { tableData } = this.state;
    tableData.data[dataIndex] = values;
    this.setState({
      visible: true,
    });
    axios
      .post('/services/com.lee.tac.facade.TestcaseApi', {
        method: 'updateTestCase',
        id: '1',
        jsonrpc: '2.0',
        params: [{
          requestData: {
            id: values.id,
            projectName: values.projectName,
            author: values.author,
            name: values.name,
          },
        }],
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
      .post('/services/com.lee.tac.facade.TestcaseApi', {
        method: 'deleteTestCase',
        id: '1',
        jsonrpc: '2.0',
        params: [{
          requestData: record.id,
        }],
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
      })
      .catch((error) => {
        console.log(error);
      });
  };

  render() {
    const { tableData } = this.state;
    const filterList = [{ text: '全部项目', value: '', default: true }];
    this.props.bindingData.projectData.data.forEach((item) => {
      const a = {};
      a.text = item.name;
      a.value = item.name;
      filterList.push(a);
    });
    return (
      <div className="tab-table">
        <IceContainer>
          <Search searchText="搜索"
            value={this.state.value}
            style={styles.search}
            onSearch={this.onSearch}
            filter={filterList}
          />
        </IceContainer>
        <IceContainer style={{ padding: '0 20px 20px' }}>
          <Loading
            style={{ display: 'block' }}
            visible={this.state.visible}
            shape="flower"
          >
            <Tab>
              <TabPane tab={this.queryCache[0].requestData.projectName || '全部项目'} key="project" >
                <CustomTable
                  dataSource={tableData.data}
                  columns={this.columns}
                  hasBorder={false}
                />
              </TabPane>
            </Tab>
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
