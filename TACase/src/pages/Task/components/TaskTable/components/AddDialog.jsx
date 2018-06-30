/*
 * @Author: zhaoli
 * @Date: 2018-04-16 19:36:42
 * @Last Modified by: zhaoli
 * @Last Modified time: 2018-06-21 10:42:48
 */
import React, { Component } from 'react';
import { Dialog, Button, Form, Input, Field, Select } from '@icedesign/base';

const FormItem = Form.Item;

export default class AddDialog extends Component {
  static displayName = 'AddDialog';

  static defaultProps = {};

  constructor(props) {
    super(props);
    this.state = {
      visible: false,
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

      if (this.state.updateContent) {
        values.content = this.state.updateContent;
      }
      this.props.addFormValues(values);
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
          size="large"
          type="primary"
          onClick={() => this.onOpen(index, record)}
        >
          增加
        </Button>
        <Dialog
          style={{ width: 640 }}
          visible={this.state.visible}
          onOk={this.handleSubmit}
          closable="esc,mask,close"
          onCancel={this.onClose}
          onClose={this.onClose}
          title="增加"
        >
          <Form direction="ver" field={this.field}>
            <FormItem label="测试集:" {...formItemLayout}>
              <Select style={styles.select} {...init('testcase', { rules: [{ required: true, message: '必填选项' }] })} >
                {this.props.testcase.map((item) => {
                  return (
                    <li value={item.id} key={item.id}>{item.name}</li>
                  );
                })}
              </Select>
            </FormItem>

            <FormItem label="负责人:" {...formItemLayout}>
              <Input
                {...init('author', {
                  rules: [{ required: true, message: '必填选项' }],
                })}
              />
            </FormItem>
            <FormItem label="Cron:" {...formItemLayout}>
              <Input
                {...init('cron', {
                  rules: [{ required: true, message: '必填选项' }],
                })}
              />
            </FormItem>
          </Form>
        </Dialog>
      </div>
    );
  }
}

const styles = {
  select: {
    width: '300px',
  },
  editDialog: {
    display: 'inline-block',
    marginRight: '5px',
    marginTop: '5px',
  },
};
