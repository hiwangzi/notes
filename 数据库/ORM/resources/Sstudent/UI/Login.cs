using System;
using System.Data;
using System.Linq;
using System.Windows.Forms;
using Model;

namespace UI
{
    public partial class Login : Form
    {
        private SstudentContainer sstudentContainer;
        public Login()
        {
            InitializeComponent();
            sstudentContainer = new SstudentContainer();
        }

        // 登录
        private void button_Login_Click(object sender, EventArgs e)
        {
            // 使用 EF 框架实现查询
            User u=sstudentContainer.UserSet.Where(user => user.Userame == textBox_Username.Text && user.Password == textBox_Password.Text).FirstOrDefault();
            // 如果查询不到，会返回 null
            if (u != null)
            {
                new MyProfile(u).Show();
                
            }
            else
            {
                MessageBox.Show("用户名或密码错误，请重试！");
            }
        }

        private void button_Signup_Click(object sender, EventArgs e)
        {
            new Signup().Show();
        }
    }
}
