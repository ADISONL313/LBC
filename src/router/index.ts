import { createRouter, createWebHistory } from 'vue-router'
import loginView from '@/views/universal/user/loginView.vue'
import forgetPwdView from '@/views/universal/user/forgetPwdView.vue'
import registerView from '@/views/universal/user/registerView.vue'
import homeView from '@/views/universal/homeView.vue'
import adminLoginComponent from '@/components/universal/user/adminLoginComponent.vue'
import defaulteView from '@/views/universal/defaulteView.vue'
import newsManagerView from '@/views/administrators/news/newsManagerView.vue'
import viewNewView from '@/views/ordinaryUsers/news/viewNewView.vue'
import userCenterView from '@/views/ordinaryUsers/user/userCenterView.vue'
import userAdminView from '@/views/administrators/user/userAdminView.vue'
import adminNewView from '@/views/ordinaryUsers/news/adminNewView.vue'
import adminActivityView from '@/views/administrators/activity/adminActivityView.vue'
import activityCenterView from '@/views/ordinaryUsers/activity/activityCenterView.vue'
import viewActiviryComponent from '@/components/ordinaryUsers/activity/viewActiviryComponent.vue'
import activityRegistrationAndChangeActivityView from '@/views/administrators/activity/activityRegistrationAndChangeActivityView.vue'
import donationView from '@/views/administrators/donation/donationView.vue'
import donationCenterView from '@/views/ordinaryUsers/donation/donationCenterView.vue'
import donationDetailView from '@/views/ordinaryUsers/donation/donationDetailView.vue'
import donationStateView from '@/views/administrators/donation/donationStateView.vue'
import helpCenterView from '@/views/ordinaryUsers/help/helpCenterView.vue'
import adminActivityRecordView from '@/views/administrators/activity/adminActivityRecordView.vue'
import adminHelpCenterView from '@/views/administrators/help/adminHelpCenterView.vue'
import helpDetailView from '@/views/ordinaryUsers/help/helpDetailView.vue'
import volunteerHelpCenterView from '@/views/ordinaryUsers/help/volunteerHelpCenterView.vue'
import newsListComponent from '@/components/ordinaryUsers/news/newsListComponent.vue'
import activityListComponent from '@/components/ordinaryUsers/activity/activityListComponent.vue'
import agreement from '@/components/universal/agreement.vue'
import more from '@/views/universal/more.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/home'
    },
    {
      path: '/login',
      name: 'login',
      component: loginView,
    },
    {
      path: '/forgetPwd',
      name: 'forgetPwd',
      component: forgetPwdView
    },
    {
      path: '/register',
      name: 'register',
      component: registerView
    },
    {
      path: '/adminLogin',
      component: adminLoginComponent
    },
    {
      path: '/home',
      name: 'home',
      component: homeView,
      redirect: '/home/defaulte',
      children: [
        {
          path: 'defaulte',
          component: defaulteView
        },
        {
          path: 'newsManager',
          component: newsManagerView
        },
        {
          path: 'viewNew',
          name: 'viewNew',
          component: viewNewView
        },
        {
          path: 'userCenter',
          name: 'userCenter',
          component: userCenterView
        },
        {
          path: 'userAdmin',
          component: userAdminView
        },
        {
          path: 'adminNew',
          component: adminNewView
        },
        {
          path: 'adminActivity',
          component: adminActivityView
        },
        {
          path: 'activityCenter',
          component: activityCenterView
        },
        {
          path: 'viewActivity',
          name:'viewActivity',
          component: viewActiviryComponent
        },
        {
          path: 'arAndac',
          name: 'arAndac',
          component: activityRegistrationAndChangeActivityView
        },
        {
          path: 'donation',
          component: donationView
        },
        {
          path: 'donationCenter',
          component: donationCenterView
        },
        {
          path: 'donationDetail',
          name: 'donationDetail',
          component: donationDetailView
        },
        {
          path: 'donationState',
          component: donationStateView
        },
        {
          path: 'help',
          component: helpCenterView
        },
        {
          path: 'adminActivityRecord',
          component: adminActivityRecordView
        },
        { path: 'adminHelp', component: adminHelpCenterView },
        {path:'helpDetail',component:helpDetailView},
        {path:'volunteerHelpCenter',component:volunteerHelpCenterView},
        {
          path:'newsList',
          component:newsListComponent
        },
        {
          path:'activityList',
          component:activityListComponent
        },
        {
          path:'more',
          component:more
        }
      ]
    }
  ]
})

// 定义允许未登录访问的路由路径数组
const publicRoutes = ['/home/defaulte', '/login', '/forgetPwd', '/register', '/adminLogin','/home/viewNew','/home/newsList','/home/activityList','/home/viewActivity','/home/more'];

router.beforeEach((to, from, next) => {
  const isLoggedIn = sessionStorage.getItem('token') !== null;
  if (!isLoggedIn && !publicRoutes.includes(to.path)) {
    alert('请登录')
    next('/login');
  } else {
    next();
  }
});

export default router;