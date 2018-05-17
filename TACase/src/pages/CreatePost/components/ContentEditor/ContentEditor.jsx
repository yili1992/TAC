import React, { Component } from 'react';
import { hashHistory } from 'react-router';
import IceContainer from '@icedesign/container';
import DataBinder from '@icedesign/data-binder';
import axios from 'axios';
import { Input, Grid, Form, Button, Select, Upload, Feedback, Loading } from '@icedesign/base';
import {
  FormBinderWrapper as IceFormBinderWrapper,
  FormBinder as IceFormBinder,
  FormError as IceFormError,
} from '@icedesign/form-binder';

const { Combobox } = Select;
const { Row, Col } = Grid;
const FormItem = Form.Item;
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
      data: [{
        id: 0,
        name: '请输入项目',
      }],
      page: {
        count: 2,
      },
    },
  },
})
export default class ContentEditor extends Component {
  static displayName = 'ContentEditor';

  static propTypes = {};

  static defaultProps = {};

  constructor(props) {
    super(props);
    this.state = {
      visible: false,
      value: {
        title: '',
        author: '',
        body: '',
        project: '',
      },
    };
  }

  componentDidMount() {
    this.props.updateBindingData('projectData');
  }

  formChange = (value) => {
    this.setState({
      value,
    });
  };

  onSuccess = (res) => {
    const data = this.state.value;
    data.body = res.imgURL;
    this.setState({ value: data });
    console.log('onSuccess callback : ', res);
  }

  onInputUpdate = (value) => {
    const data = this.state.value;
    data.project = value;
    this.setState({ value: data });
  }

  handleSubmit = () => {
    this.postForm.validateAll((errors, values) => {
      console.log('errors', errors, 'values', values);
      if (errors) {
        return false;
      }
      this.setState({
        visible: true,
      });
      axios
        .post('/services/com.lee.tac.facade.TestcaseApi', {
          method: 'saveTestCase',
          id: '1',
          jsonrpc: '2.0',
          params: [{
            requestData: {
              projectName: values.project,
              author: values.author,
              name: values.title,
              content: values.body,
            },
          }],
        })
        .then((response) => {
          if (response.data.result.code === '00000') {
            Toast.success(response.data.result.msg);
            hashHistory.push('/post/list');
          } else {
            Toast.error(response.data.result.msg);
            console.log(response.data);
            this.setState({
              visible: false,
            });
          }
        })
        .catch((error) => {
          console.log(error);
          this.setState({
            visible: false,
          });
        });
    });
  };

  render() {
    const { projectData } = this.props.bindingData;
    return (
      <div className="content-editor">
        <IceFormBinderWrapper
          ref={(refInstance) => {
            this.postForm = refInstance;
          }}
          value={this.state.value}
          onChange={this.formChange}
        >
          <Loading
            style={{ display: 'block' }}
            visible={this.state.visible}
            shape="dot-circle"
          >
            <IceContainer title="增加测试集">
              <Form labelAlign="top" style={styles.form}>
                <Row>
                  <Col span="11">
                    <FormItem label="标题" required>
                      <IceFormBinder name="title" required message="标题必填">
                        <Input
                          placeholder="这里填写测试集标题"
                        />
                      </IceFormBinder>
                      <IceFormError name="title" />
                    </FormItem>
                  </Col>
                </Row>
                <Row>
                  <Col span="11">
                    <FormItem label="作者" required>
                      <IceFormBinder
                        name="author"
                        required
                        message="作者信息必填"
                      >
                        <Input placeholder="填写作者名称" />
                      </IceFormBinder>
                      <IceFormError name="author" />
                    </FormItem>
                  </Col>
                  <Col span="11" offset="2">
                    <FormItem label="项目" required>
                      <IceFormBinder
                        name="project"
                        required
                        message="项目信息必填"
                      >
                        <Combobox
                          onInputUpdate={this.onInputUpdate}
                        >
                          {projectData.data.map((item) => {
                            return (
                              <li value={item.name} key={item.id}>{item.name}</li>
                            );
                          })}
                        </Combobox>
                      </IceFormBinder>
                    </FormItem>
                  </Col>
                </Row>
                <FormItem label="配置内容" required>
                  <IceFormBinder
                    name="body"
                    required
                    message="配置文件必须上传"
                  >
                    <Upload
                      limit={1}
                      // action="//127.0.0.1:8088/upload"  // 测试时候使用
                      action="/upload" // 构建发布使用
                      onSuccess={this.onSuccess}
                    >
                      <Button type="primary" style={{ margin: '0 0 10px' }}>
                        上传文件
                      </Button>
                    </Upload>
                  </IceFormBinder>
                </FormItem>
                <FormItem label=" ">
                  <Button type="primary" onClick={this.handleSubmit}>
                      发布测试集
                  </Button>
                </FormItem>
              </Form>
            </IceContainer>
          </Loading>
        </IceFormBinderWrapper>
      </div>
    );
  }
}

const styles = {
  form: {
    marginTop: 30,
  },
  cats: {
    width: '100%',
  },
};
