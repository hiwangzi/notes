using System;
using System.Data;
using System.Linq;
using System.Windows.Forms;
using Model;

namespace UI
{
    public partial class MyProfile : Form
    {
        private User user;
        private SstudentContainer sstudentContainer;

        public MyProfile(User u)
        {
            InitializeComponent();
            sstudentContainer = new SstudentContainer();

            // 首先使用 sstudentContainer 查找到该项记录（对象）
            user = sstudentContainer.UserSet.Where(uu => uu.Id == u.Id).FirstOrDefault();

            // 显示数据
            this.textBox_Address.Text = user.Profile.Address;
            this.textBox_FullName.Text = user.Profile.姓名;
            this.textBox_Hobby.Text = user.Profile.Hobby;
            this.textBox_Password.Text = user.Password;
            this.textBox_PhoneNumber.Text = user.Profile.PhoneNumber;
            this.textBox_Sex.Text = user.Profile.Sex;
            this.textBox_StudentNumber.Text = user.Profile.StudentNumber;
            this.textBox_Username.Text = user.Userame;
            

        }

        // 更新用户资料
        private void button_Update_Click(object sender, EventArgs e)
        {
            // 使用 EF 框架实现更新数据
            try
            {
                // 需要首先使用 sstudentContainer 查找到该项记录（对象），然后对其进行修改
                User u = sstudentContainer.UserSet.Where(user => user.Id == this.user.Id).FirstOrDefault();
                Profile p = u.Profile;

                u.Password = this.textBox_Password.Text;
                u.Userame = this.textBox_Username.Text;

                p.Address = this.textBox_Address.Text;
                p.Hobby = this.textBox_Hobby.Text;
                p.PhoneNumber = this.textBox_PhoneNumber.Text;
                p.Sex = this.textBox_Sex.Text;
                p.StudentNumber = this.textBox_StudentNumber.Text;
                p.姓名 = this.textBox_FullName.Text;

                // 保存更改进入数据库
                sstudentContainer.SaveChanges();

                MessageBox.Show("更新资料成功！");
            }
            catch (Exception)
            {
                // 发生异常，更新失败
                MessageBox.Show("更新资料失败！");
                throw;
            }
            
        }

        // 删除所有用户资料
        private void button_Delete_Click(object sender, EventArgs e)
        {
            // 使用 EF 框架实现删除数据
            try
            {
                // 需要首先使用 sstudentContainer 查找到该项记录（对象），然后将其删除
                User u = sstudentContainer.UserSet.Where(user => user.Id == this.user.Id).FirstOrDefault();
                Profile p = u.Profile;

                // 删除用户
                sstudentContainer.ProfileSet.Remove(p);
                sstudentContainer.UserSet.Remove(u);

                // 保存删除操作进入数据库
                sstudentContainer.SaveChanges();

                MessageBox.Show("再见，删除成功！");

                this.Dispose();
            }
            catch (Exception)
            {
                // 发生异常，删除失败
                MessageBox.Show("抱歉，删除失败！");
                throw;
            }
        }


    }
}
