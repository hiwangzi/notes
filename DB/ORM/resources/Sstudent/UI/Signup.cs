using System;
using System.Windows.Forms;
using Model;

namespace UI
{
    public partial class Signup : Form
    {
        private SstudentContainer sstudentContainer;

        public Signup()
        {
            InitializeComponent();
            sstudentContainer = new SstudentContainer();
        }

        // 注册
        private void button_Signup_Click(object sender, EventArgs e)
        {
            try
            {
                // 使用 EF 框架实现添加新数据功能
                User u = new User();
                Profile p = new Profile();

                u.Password = this.textBox_Password.Text;
                u.Userame = this.textBox_Username.Text;

                p.Address = this.textBox_Address.Text;
                p.Hobby = this.textBox_Hobby.Text;
                p.PhoneNumber = this.textBox_PhoneNumber.Text;
                p.Sex = this.textBox_Sex.Text;
                p.StudentNumber = this.textBox_StudentNumber.Text;
                p.姓名 = this.textBox_FullName.Text;

                u.Profile = p;

                // 添加两个新对象分别进入两张表
                sstudentContainer.UserSet.Add(u);
                // 因为 EF 会自动将关联的对象同时加入数据库，所以无需手动增加
                //sstudentContainer.ProfileSet.Add(p);

                // 保存新增记录进入数据库
                sstudentContainer.SaveChanges();

                MessageBox.Show("注册成功！");

                this.Close();
            }
            catch (Exception)
            {
                // 发生异常，插入失败
                MessageBox.Show("抱歉，注册失败！");
                throw;
            }
            

        }
    }
}
