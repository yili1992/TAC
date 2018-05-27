/*
 * @Author: zhao lee
 * @Date: 2018-04-16 19:36:42
<<<<<<< HEAD
 * @Last Modified by: zhaoli@leoao.com
 * @Last Modified time: 2018-05-19 10:13:39
=======
 * @Last Modified by: zhao lee
 * @Last Modified time: 2018-04-17 20:15:41
>>>>>>> master
 */
import React, { Component } from 'react';
import { Dialog, Button, Form, Input, Field, Select, Upload } from '@icedesign/base';

const FormItem = Form.Item;

export default class EditDialog extends Component {
  static displayName = 'EditDialog';

  static defaultProps = {};

  constructor(props) {
    super(props);
    this.state = {
      visible: false,
      dataIndex: null,
      updateContent: null,
    };
    this.field = new Field(this);
  }

  handleSubmit = () => {
    this.field.validate((errors, values) => {
      if (errors) {
        console.log('Errors in form!!!');
        return;
      }

      const { dataIndex } = this.state;
      if (this.state.updateContent) {
        values.content = this.state.updateContent;
      }
      this.props.getFormValues(dataIndex, values);
      this.setState({
        visible: false,
      });
    });
  };

  onSuccess = (res) => {
    this.setState({
      updateContent: res.imgURL,
    });
    console.log('onSuccess callback : ', res);
  }

  onOpen = (index, record) => {
    this.field.setValues({ ...record });
    this.setState({
      visible: true,
      dataIndex: index,
    });
  };

  onClose = () => {
    this.setState({
      visible: false,
    });
  };

  render() {
    const init = this.field.init;
    const { index, record } = this.props;
    const formItemLayout = {
      labelCol: {
        fixedSpan: 6,
      },
      wrapperCol: {
        span: 14,
      },
    };
    return (
      <div style={styles.editDialog}>
        <Button
          size="small"
          type="secondary"
          onClick={() => this.onOpen(index, record)}
        >
          编辑
        </Button>
        <Dialog
          style={{ width: 640 }}
          visible={this.state.visible}
          onOk={this.handleSubmit}
          closable="esc,mask,close"
          onCancel={this.onClose}
          onClose={this.onClose}
          title="编辑"
        >
          <Form direction="ver" field={this.field}>
            <FormItem label="标题：" {...formItemLayout}>
              <Input
                {...init('name', {
                  rules: [{ required: true, message: '必填选项' }],
                })}
              />
            </FormItem>
            <FormItem label="项目：" {...formItemLayout}>
              <Select {...init('projectName', { rules: [{ required: true, message: '必填选项' }] })} >
                {this.props.projects.map((item) => {
                  return (
                    <li value={item.name} key={item.id}>{item.name}</li>
                  );
                })}
              </Select>
            </FormItem>

            <FormItem label="作者：" {...formItemLayout}>
              <Input
                {...init('author', {
                  rules: [{ required: true, message: '必填选项' }],
                })}
              />
            </FormItem>

            <FormItem label="配置信息：" {...formItemLayout}>
              <Input disabled
                placeholder="disabled"
                {...init('content', {
                  rules: [{ required: true, message: '必填选项' }],
                })}
              />
              <Upload
                limit={1}
                action="/upload" // 构建发布使用
                onSuccess={this.onSuccess}
              >
                <Button type="primary" style={{ margin: '0 0 10px' }}>
                  修改配置文件
                </Button>
              </Upload>
            </FormItem>
          </Form>
        </Dialog>
      </div>
    );
  }
}

const styles = {
  editDialog: {
    display: 'inline-block',
    marginRight: '5px',
  },
};
