import React, { PureComponent } from 'react';
import { Link } from 'react-router';

export default class Logo extends PureComponent {
  render() {
    return (
      <div className="logo">
        <Link to="/" style={{ fontSize: '30px', fontWeight: 'bold', color: 'white' }}>
          TAC自动化测试管理系统
        </Link>
      </div>
    );
  }
}
