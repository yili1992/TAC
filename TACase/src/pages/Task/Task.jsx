import React, { Component } from 'react';
import TaskTable from './components/TaskTable';
import './Task.scss';

export default class Task extends Component {
  static displayName = 'Task';

  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    return (
      <div className="post-list-page">
        <TaskTable />
      </div>
    );
  }
}
