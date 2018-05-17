

import React, { Component } from 'react';
import ContentEditor from './components/ContentEditor';

import './CreatePost.scss';

export default class CreatePost extends Component {
  static displayName = 'CreatePost';

  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    return (
      <div className="create-post-page">
        <ContentEditor />
      </div>
    );
  }
}
