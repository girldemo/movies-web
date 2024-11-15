import styles from './Footer.module.css'
import logo from './assets/roll-film.png'

function Footer(){
    return (
        <footer className={styles.footer}>
            <div className={styles.footer_container}>
                <img src={logo} alt="logo" className={styles.img_logo}/>
                <p className={styles.footer_p}>Tận hưởng trải nghiệm xem phim mới nhất 
                    miễn phí ngay tại Movie và dành thời gian 
                    thư giãn chill cùng gia đình và bạn bè. 
                    Với một thư viện phim phong phú</p>
            </div>
            <div className={styles.footer_container}>
                <ul>
                    <li><h4>Phim mới</h4></li>
                    <li>Phim chiếu rạp</li>
                    <li>Phim bộ</li>
                    <li>Phim hành động</li>
                    <li>Phim viễn tưởng</li>
                    <li>Phim tâm lý</li>
                    <li>Phim hài hước</li>
                </ul>
            </div>
            <div className={styles.footer_container}>
                <ul>
                    <li><h4>Phim hay</h4></li>
                    <li>Phim Mỹ</li>
                    <li>Phim Hàn Quốc</li>
                    <li>Phim Trung Quốc</li>
                    <li>Phim Thái Lan</li>
                    <li>Phim Việt Nam</li>
                    <li>Phim Ma Kinh Dị</li>
                    <li>Phim Hoạt Hình</li>
                </ul>
            </div>
            <div className={styles.footer_container}>
                <ul>
                    <li><h4>Phim hot</h4></li>
                    <li><a href="">Về chúng tôi</a></li>
                    <li><a href="">Movie</a></li>
                </ul>
            </div>
            <div className={styles.footer_container}>
                <ul>
                    <li><h4>Trợ giúp</h4></li>
                    <li>Hỏi đáp</li>
                    <li>Liên hệ</li>
                    <li>Tin tức</li>
                </ul>
            </div>
            <div className={styles.footer_container}>
                <ul>
                    <li><h4>Thông tin</h4></li>
                    <li>Điều khoản sử dụng</li>
                    <li>Chính sách riêng tư</li>
                    <li>Khiếu nại bản quyền</li>
                    <li>©2024 Movie</li>
                </ul>
            </div>
        </footer>
    );
}

export default Footer