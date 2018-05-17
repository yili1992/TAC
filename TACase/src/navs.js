// <!-- auto generated navs start -->
const autoGenHeaderNavs = [];
const autoGenAsideNavs = [];

// <!-- auto generated navs end -->

const customHeaderNavs = [
  {
    text: '首页',
    to: '/',
    icon: 'home',
  },
];

const customAsideNavs = [
  {
    text: 'Dashboard',
    to: '/',
    icon: 'home',
  },
  {
    text: '测试集管理',
    to: '/post',
    icon: 'copy',
    children: [
      { text: '添加测试集', to: '/post/create' },
      { text: '测试集列表', to: '/post/list' },
    ],
  },
];

function transform(navs) {
  // custom logical
  return [...navs];
}

export const headerNavs = transform([
  ...autoGenHeaderNavs,
  ...customHeaderNavs,
]);

export const asideNavs = transform([...autoGenAsideNavs, ...customAsideNavs]);
