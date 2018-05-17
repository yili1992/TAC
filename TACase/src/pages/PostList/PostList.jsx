import React, { Component } from 'react';
import TabTable from './components/TabTable';

import './PostList.scss';

export default class PostList extends Component {
  static displayName = 'PostList';

  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    return (
      <div className="post-list-page">
        <TabTable />
      </div>
    );
  }
}
