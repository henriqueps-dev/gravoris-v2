package br.com.gravoris.loader;

import br.com.gravoris.model.Categoria;
import br.com.gravoris.model.Produto;
import br.com.gravoris.repository.ProdutoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * DataLoader insere o catálogo de superesportivos de luxo ao iniciar a
 * aplicação.
 */
@Component
public class DataLoader implements CommandLineRunner {

        private final ProdutoRepository produtoRepository;

        public DataLoader(ProdutoRepository produtoRepository) {
                this.produtoRepository = produtoRepository;
        }

        @Override
        public void run(String... args) {
                if (produtoRepository.count() > 0 || produtoRepository.existsByMarca("Rimac")) {
                        return;
                }

                List<Produto> produtos = List.of(
                                // BUGATTI
                                luxury("Chiron Super Sport", "Bugatti", "Super Sport",
                                                "12500000", "W16 8.0L Quad-Turbo", 1600, "1600 Nm", 440, "2.3s",
                                                "https://4kwallpapers.com/images/wallpapers/bugatti-chiron-4500x3000-9869.jpg",
                                                2024,
                                                "O ápice da engenharia francesa. O Chiron Super Sport combina artesanato exclusivo com performance absoluta."),

                                luxury("W16 Mistral Roadster", "Bugatti", "Mistral",
                                                "27000000", "W16 8.0L Quad-Turbo", 1600, "1600 Nm", 420, "2.4s",
                                                "https://www.postoakmotors.com/staticmedia/65e63d59809dad959e1b7ac1/c4a1259e5cdb0e9e8a8a4e4cbc9ed9c3.webp", 2024,
                                                "A despedida oficial do motor W16 roadster da Bugatti, combinando elegância a céu aberto."),

                                    luxury("Bolide Track Car", "Bugatti", "Bolide",
                                                "25000000", "W16 8.0L Quad-Turbo", 1850, "1850 Nm", 501, "2.17s",
                                                "https://www.topgear.com/sites/default/files/images/news-article/2020/10/b98c78ffd730bcece647d7128bb42514/20_bolide_garage_3.jpg", 2024,
                                                "Um monstro feito exclusivamente para as pistas de corrida com relação peso-potência de carro de F1."),

                                // FERRARI
                                luxury("SF90 Stradale", "Ferrari", "Stradale",
                                                "4900000", "V8 4.0L Bi-Turbo Hybrid", 1000, "800 Nm", 340, "2.5s",
                                                "https://cdn.motor1.com/images/mgl/xqq24z/s3/novitec-ferrari-sf90-stradale-2022.jpg", 2023,
                                                "A definição de performance híbrida da marca do Cavalo Empinado. Edição extremamente limitada."),

                                luxury("LaFerrari", "Ferrari", "Aperta",
                                                "16000000", "V12 6.3L Hybrid", 963, "900 Nm", 350, "2.4s",
                                                "https://s2-autoesporte.glbimg.com/njAfNelQ-vl-J98MIEBuMecd__0=/i.s3.glbimg.com/v1/AUTH_cf9d035bf26b4646b105bd958f32089d/internal_photos/bs/2024/K/V/TyZJ3tR22zBuYCijkSlA/ferrari-laferrari-brasil-paito.jpg", 2018,
                                                "O primeiro supercarro híbrido da Ferrari, combinando tecnologia de F1 com luxo incomparável."),

                                luxury("Daytona SP3", "Ferrari", "Icona",
                                                "14500000", "V12 6.5L Aspirado", 840, "697 Nm", 340, "2.85s",
                                                "https://www.amalgamcollection.com/cdn/shop/files/DSCF7656_grande.jpg?v=1726050245", 2023,
                                                "Homenagem aos protótipos esportivos dos anos 60. Design escultural com o motor V12 mais potente da marca."),

                                luxury("812 Competizione", "Ferrari", "812",
                                                "5800000", "V12 6.5L Aspirado", 830, "692 Nm", 340, "2.85s",
                                                "https://i.bstr.es/highmotor/2024/09/ferrari-812-competizione-tailor-made-5.jpg", 2023,
                                                "O auge dos motores V12 dianteiros aspirados da Ferrari. Um verdadeiro tributo à potência pura."),

                                luxury("Roma Spider", "Ferrari", "Spider",
                                                "2400000", "V8 3.9L Bi-Turbo", 620, "760 Nm", 320, "3.4s",
                                                "https://carrosbemmontados.com.br/wp-content/uploads/2024/10/ferrari-roma-spider-tailor-made-2024-1.jpg", 2024,
                                                "Elegância atemporal inspirada na 'Dolce Vita' italiana dos anos 50 e 60, agora conversível."),

                                luxury("296 GTB Assetto Fiorano", "Ferrari", "296 GTB",
                                                "2900000", "V6 3.0L Hybrid", 830, "740 Nm", 330, "2.9s",
                                                "https://carconfigurator.ferrari.com/f171/724d318d286af3e4e022e05d29bcda861bf95191a23445db49f5d03cf6d3ac03-Blackroom-Day-beautyshot_ext_frontleft-2d.jpg", 2023,
                                                "O motor V6 híbrido com a dinâmica mais avançada e divertida das ruas modernas."),

                                luxury("Purosangue V12", "Ferrari", "Purosangue",
                                                "6200000", "V12 6.5L Aspirado", 725, "716 Nm", 310, "3.3s",
                                                "https://s2-autoesporte.glbimg.com/xZA3BFHBifjfNDaVeZoDuGdg_0Y=/0x0:800x500/888x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_cf9d035bf26b4646b105bd958f32089d/internal_photos/bs/2022/Z/6/bULJdlRcOEktT3JwO9VA/1024x768-01-front34-1a-hr.jpg", 2024,
                                                "O primeiro quatro portas e quatro lugares da Ferrari. Equipado com um motor V12 de som inconfundível.",
                                                Categoria.SUV),

                                // LAMBORGHINI
                                luxury("Aventador SVJ", "Lamborghini", "SVJ",
                                                "8200000", "V12 6.5L Aspirado", 770, "720 Nm", 350, "2.8s",
                                                "https://imgcdnblog.carmudi.com.ph/wp-content/uploads/2021/07/09174038/Lamborghini-Aventador_LP780-4_Ultimae-2022-1024-03.jpg", 2022,
                                                "Com design agressivo e aerodinâmica ativa, o Aventador SVJ redefine os limites de desempenho de um supercarro V12."),

                                luxury("Revuelto", "Lamborghini", "LB744",
                                                "4800000", "V12 6.5L Hybrid", 1015, "807 Nm", 350, "2.5s",
                                                "https://www.webmotors.com.br/wp-content/uploads/2023/03/29151922/Lamborghini-Revuelto-1.webp", 2024,
                                                "O sucessor do Aventador. O primeiro superesportivo híbrido V12 plug-in da lendária marca de Sant'Agata."),

                                luxury("Huracán STO", "Lamborghini", "STO",
                                                "3200000", "V10 5.2L Aspirado", 640, "565 Nm", 310, "3.0s",
                                                "https://motorshow.com.br/wp-content/uploads/sites/2/2020/11/lamborghini-huracan-sto-3.jpg", 2023,
                                                "Homologado para as ruas, mas nascido nas pistas do campeonato Super Trofeo da Lamborghini."),

                                luxury("Urus Performante", "Lamborghini", "Performante",
                                                "4200000", "V8 4.0L Bi-Turbo", 666, "850 Nm", 306, "3.3s",
                                                "https://s2-autoesporte.glbimg.com/uHvsVM6fjYsd8-xtveWchDo4-OU=/0x0:1800x1012/888x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_cf9d035bf26b4646b105bd958f32089d/internal_photos/bs/2022/Q/8/Bb51JTSmuPuDs4enaVtg/lamborghini-urus-performante-01.jpg", 2023,
                                                "O super SUV que une o DNA de superesportivo da Lamborghini com a versatilidade de um utilitário de luxo.",
                                                Categoria.SUV),

                                luxury("Urus S", "Lamborghini", "Urus",
                                                "3800000", "V8 4.0L Bi-Turbo", 666, "850 Nm", 305, "3.5s",
                                                "https://cdn.motor1.com/images/mgl/YA7AoW/s1/lamborghini-urus-s.jpg", 2023,
                                                "A evolução do SUV superesportivo original da Lamborghini. Luxo, presença e velocidade brutal.",
                                                Categoria.SUV),

                                // PORSCHE
                                luxury("911 GT3 RS", "Porsche", "GT3 RS",
                                                "1900000", "F6 4.0L Boxer Aspirado", 525, "465 Nm", 296, "3.2s",
                                                "https://cdn.motor1.com/images/mgl/Vzz1qK/s3/most-expensive-2023-porsche-911-gt3-rs.jpg", 2023,
                                                "Nascido nas pistas de corrida. O 911 GT3 RS oferece aerodinâmica extrema e precisão cirúrgica para as ruas."),

                                luxury("918 Spyder", "Porsche", "918",
                                                "9800000", "V8 4.6L Hybrid", 887, "1280 Nm", 345, "2.6s",
                                                "918 Spyder", 2015,
                                                "A obra-prima tecnológica da Porsche. Tração integral sob demanda e tecnologia híbrida revolucionária."),

                                luxury("Carrera GT", "Porsche", "Carrera GT",
                                                "7500000", "V10 5.7L Aspirado", 612, "590 Nm", 330, "3.9s",
                                                "https://imagedelivery.net/nkaANmEhdg2ZZ4vhQHp4TQ/afaca42f-69a1-44a2-0b06-f14ac3dc4300/public", 2005,
                                                "Lendário supercarro analógico com motor derivado da F1 e um dos roncos mais bonitos da história."),

                                luxury("911 S/T Limited", "Porsche", "911 S/T",
                                                "2200000", "F6 4.0L Boxer Aspirado", 525, "465 Nm", 300, "3.7s",
                                                "https://newsroom.porsche.com/.imaging/mte/porsche-templating-theme/teaser_720x406x2/dam/pnr/2023/Products/911-S-T/911-S-T-studio/S23_1162_fine.jpg/jcr:content/S23_1162_fine.jpg", 2024,
                                                "Edição purista de aniversário da Porsche, combinando o motor do GT3 RS com câmbio manual leve."),

                                luxury("Taycan Turbo S", "Porsche", "Taycan",
                                                "1100000", "Dois Motores Elétricos", 761, "1050 Nm", 260, "2.8s",
                                                "https://robbreport.com/wp-content/uploads/2024/02/25taycan00.jpg", 2023,
                                                "A performance elétrica definitiva da Porsche. Aceleração instantânea com a dinâmica clássica da marca.",
                                                Categoria.SEDAN),

                                luxury("Cayenne Turbo GT", "Porsche", "Cayenne",
                                                "1300000", "V8 4.0L Bi-Turbo", 640, "850 Nm", 300, "3.3s",
                                                "https://cdn.motor1.com/images/mgl/KbMRGb/s1/porsche-cayenne-turbo-gt-2025-br.webp", 2023,
                                                "O SUV de recordes em pistas. Ajustado pela divisão GT da Porsche para máxima dinâmica lateral.",
                                                Categoria.SUV),

                                // MCLAREN
                                luxury("765LT Coupe", "McLaren", "765LT",
                                                "5500000", "V8 4.0L Bi-Turbo", 765, "800 Nm", 330, "2.8s",
                                                "photo-1562591176-b2b1d6f53e2f", 2022,
                                                "Leve, focado e incrivelmente rápido. O 765LT é um dos carros de rua mais puros e envolventes já construídos."),

                                luxury("McLaren P1", "McLaren", "P1",
                                                "8500000", "V8 3.8L Bi-Turbo Hybrid", 916, "900 Nm", 350, "2.8s",
                                                "https://cars-assets-production.mclaren.com/2696/mclaren-legacy-765lt-hero.jpg", 2015,
                                                "Parte da 'Santíssima Trindade' dos hipercarros, oferecendo aerodinâmica de ponta e tecnologia de ponta."),

                                luxury("McLaren Senna", "McLaren", "Senna",
                                                "9500000", "V8 4.0L Bi-Turbo", 800, "800 Nm", 340, "2.8s",
                                                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSVRrhBh9UPLhdwA_YEU0f9RAPqQ0agSUZJ_g&s", 2019,
                                                "Desenvolvido com o único propósito de ser o carro de rua mais focado em pista já feito, homenageando Ayrton Senna."),

                                luxury("Speedtail", "McLaren", "Hyper-GT",
                                                "18000000", "V8 4.0L Hybrid", 1070, "1150 Nm", 403, "3.0s",
                                                "https://cars-assets-production.mclaren.com/2691/mclaren-ultimate-speedtail-hero-expanded.jpg", 2021,
                                                "O McLaren de rua mais aerodinâmico e rápido de todos os tempos. Assento central de condução."),

                                // ASTON MARTIN
                                luxury("Valkyrie", "Aston Martin", "AM-RB 001",
                                                "18500000", "V12 6.5L Aspirado Cosworth", 1155, "900 Nm", 400, "2.6s",
                                                "https://www.astonmartin.com/-/media/models---valkyrie-2024/valkyrie-july-24-uplift/valkyrie-0402/valkyrie-hero-desktop.jpg?mw=1920&rev=47f17ecdaafd47478a727c27de7e87a8&hash=197144ED374741B5A8C44BF6F3746F5A", 2023,
                                                "Um carro de Formula 1 homologado para as ruas, desenhado pelo lendário projetista Adrian Newey."),

                                luxury("DBS 770 Ultimate", "Aston Martin", "770 Ultimate",
                                                "2800000", "V12 5.2L Bi-Turbo", 770, "900 Nm", 340, "3.4s",
                                                "https://www.astonmartinsaopaulo.com.br/pub/galeria/dbs-770-ul_1_230209_4505.jpg", 2023,
                                                "O Gran Turismo definitivo. O motor V12 de produção mais potente da Aston Martin com tiragem limitada."),

                                luxury("DB12 Coupe", "Aston Martin", "DB12",
                                                "2600000", "V8 4.0L Bi-Turbo", 680, "800 Nm", 325, "3.6s",
                                                "https://asset.skoiy.com/tbowjqdgqpxirbte/smaqvenxqhik.jpg?w=1560&q=80", 2024,
                                                "O primeiro 'Super Tourer' do mundo. Luxo sob medida e dinâmica esportiva altamente afiada."),

                                luxury("DBX707", "Aston Martin", "DBX",
                                                "3450000", "V8 4.0L Bi-Turbo", 707, "900 Nm", 310, "3.3s",
                                                "https://miamiimports.com.br/wp-content/uploads/2024/01/ASTON-MARTIN-DBX-707-TRASEIRA-ESQUERDA.png", 2023,
                                                "O SUV de luxo mais potente do mundo. Desempenho absurdo com o refinamento clássico britânico.",
                                                Categoria.SUV),

                                // ROLLS-ROYCE
                                luxury("Phantom EWB", "Rolls-Royce", "Phantom",
                                                "7500000", "V12 6.75L Bi-Turbo", 571, "900 Nm", 250, "5.4s",
                                                "https://www.mansory.com/cdn-cgi/image/format=avif,quality=90/https://cdn.prod.website-files.com/661d6e0d2e84ef511db18f17/6818c0fd2e6c6f89d6bb3bff_MANSORY%2520Phantom%2520VIII-Thumbnail-001.webp", 2023,
                                                "O padrão ouro do luxo automotivo mundial. Silêncio incomparável e conforto supremo em cada detalhe.",
                                                Categoria.SEDAN),

                                luxury("Spectre Coupe", "Rolls-Royce", "Spectre",
                                                "4500000", "Dois Motores Elétricos", 584, "900 Nm", 250, "4.5s",
                                                "https://www.elektrischeauto.be/src/Frontend/Files/Cache/photoswipe_max_size/src/Frontend/Files/MediaLibrary/05/rolls-royce-black-badge-spectre-series-ii-6.webp", 2024,
                                                "O primeiro Rolls-Royce totalmente elétrico da história. O futuro do luxo silencioso e refinado.",
                                                Categoria.SEDAN),

                                luxury("Cullinan Black Badge", "Rolls-Royce", "Cullinan",
                                                "5900000", "V12 6.75L Bi-Turbo", 600, "900 Nm", 250, "5.2s",
                                                "https://img2.icarros.com/dbimg/imgadicionalnoticia/4/113036_1.jpg", 2023,
                                                "O 'Rolls-Royce de todos os terrenos', com detalhes escurecidos e maior foco em dinâmica de condução.",
                                                Categoria.SUV),

                                // BENTLEY
                                luxury("Continental GT Speed", "Bentley", "GT Speed",
                                                "2200000", "W12 6.0L Bi-Turbo", 659, "900 Nm", 335, "3.6s",
                                                "https://www.automaistv.com.br/wp-content/uploads/2025/11/Bentley-Superesports-Frente-1320x713.webp", 2023,
                                                "Luxo insuperável com potência de sobra. O W12 de produção da Bentley entrega conforto e desempenho soberbos."),

                                luxury("Flying Spur Speed", "Bentley", "Speed",
                                                "2500000", "W12 6.0L Bi-Turbo", 635, "900 Nm", 333, "3.8s",
                                                "https://www.topgear.com/sites/default/files/2024/09/New%20Flying%20Spur%20-%205.jpg", 2023,
                                                "O sedã de luxo mais rápido da Bentley. Combinação perfeita de esportividade e opulência refinada.",
                                                Categoria.SEDAN),

                                luxury("Bentayga Speed", "Bentley", "Bentayga",
                                                "2800000", "W12 6.0L Bi-Turbo", 635, "900 Nm", 306, "3.9s",
                                                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRQ3R8HpyNIZm_9Ia62z7g7jNNtUZ7fao2AEw&s", 2022,
                                                "O auge do luxo em SUV. Motor de 12 cilindros e interior luxuoso revestido à mão.",
                                                Categoria.SUV),

                                // KOENIGSEGG
                                luxury("Jesko Absolut", "Koenigsegg", "Absolut",
                                                "23000000", "V8 5.0L Bi-Turbo", 1622, "1500 Nm", 531, "2.5s",
                                                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQUC2674oSkhk1NaGdGw4mpvJ2VWGL0PROQzw&s", 2024,
                                                "O carro de produção mais rápido do mundo, projetado para quebrar a barreira dos 500 km/h."),

                                luxury("Gemera", "Koenigsegg", "Gemera",
                                                "11000000", "V8 5.0L Bi-Turbo + Elétrico", 2300, "3500 Nm", 400, "1.9s",
                                                "https://automagazin.sk/wp-content/uploads/2020/03/Koenigsegg-Gamera-08.jpg", 2025,
                                                "O primeiro 'Mega-GT' de 4 lugares do mundo. Potência inacreditável para viajar com a família."),

                                luxury("Regera", "Koenigsegg", "Regera",
                                                "14000000", "V8 5.0L Hybrid Direct Drive", 1500, "2000 Nm", 410, "2.8s",
                                                "https://racingclub.com.br/wp-content/uploads/2017/03/koenigsegg-geneva-motor-show-11-1280x721.jpg", 2020,
                                                "Hipercarro híbrido com sistema inovador Direct Drive, sem caixa de câmbio convencional."),

                                // PAGANI
                                luxury("Huayra Roadster BC", "Pagani", "Roadster BC",
                                                "21000000", "V12 6.0L Bi-Turbo AMG", 802, "1050 Nm", 380, "2.9s",
                                                "https://cdn.motor1.com/images/mgl/3XMGX/s1/pagani-huayra-roadster-bc.jpg", 2022,
                                                "Uma obra de arte sobre rodas. Fibra de carbono especial e acabamento digno de alta relojoaria."),

                                luxury("Utopia", "Pagani", "Utopia",
                                                "19000000", "V12 6.0L Bi-Turbo AMG", 864, "1100 Nm", 350, "3.0s",
                                                "https://img.odcdn.com.br/wp-content/uploads/2025/02/utopia-1-1920x1080.jpg", 2024,
                                                "O mais novo capítulo da história da Pagani. Um purista V12 com câmbio manual e design escultural."),

                                // MASERATI
                                luxury("MC20 Cielo", "Maserati", "MC20",
                                                "2800000", "V6 3.0L Bi-Turbo Nettuno", 630, "730 Nm", 320, "3.0s",
                                                "https://maserati.scene7.com/is/image/maserati/maserati/international/ownership/update-2022/mc20-cielo-accessories/169/MC20-cielo-Hero.jpg?$1400x2000$&fit=constrain", 2023,
                                                "O supercarro conversível italiano com motor de tecnologia patenteada de F1 e teto de vidro inteligente."),

                                luxury("Levante Trofeo V8", "Maserati", "Levante",
                                                "1400000", "V8 3.8L Bi-Turbo Ferrari", 580, "730 Nm", 302, "4.1s",
                                                "https://static.moniteurautomobile.be/imgcontrol/images_tmp/clients/moniteur/c1440-d720/content/medias/images/news/27000/600/50/maserati-levante-trofeo-2018-01.jpg", 2023,
                                                "O SUV italiano equipado com motor de linhagem Ferrari e um ronco apaixonante.",
                                                Categoria.SUV),

                                // RIMAC
                                luxury("Nevera", "Rimac", "Nevera",
                                                "12000000", "4 Motores Elétricos", 1914, "2360 Nm", 412, "1.81s",
                                                "https://cdn.motor1.com/images/mgl/JlgjM/s1/rimac-nevera.webp", 2023,
                                                "O hipercarro elétrico que quebrou 23 recordes de performance em um único dia. Aceleração brutal."),

                                // AUDI
                                luxury("R8 V10 GT RWD", "Audi", "GT RWD",
                                                "1800000", "V10 5.2L Aspirado", 620, "560 Nm", 320, "3.4s",
                                                "https://www.evoindia.com/evoindia/2022-10/a52b96bb-6f4d-4f37-814c-04a226506fa8/10284_A225131large.jpg", 2023,
                                                "A despedida do lendário motor V10 aspirado. Edição numerada com tração traseira exclusiva."),

                                // MERCEDES-BENZ
                                luxury("AMG GT Black Series", "Mercedes-Benz", "Black Series",
                                                "3500000", "V8 4.0L Bi-Turbo Flat-Plane", 730, "800 Nm", 325, "3.2s",
                                                "https://www.planetcarsz.com/img/carros/2022/07/mercedes-amg-gt-black-series-by-renntech-2022-01-20220705182225-1920x1080.jpg", 2021,
                                                "O monstro de Affalterbach. Aerodinâmica ajustável de corrida com recorde de tempo em Nürburgring."),

                                luxury("G 63 Edition 55", "Mercedes-Benz", "G-Class",
                                                "2100000", "V8 4.0L Bi-Turbo", 585, "850 Nm", 220, "4.5s",
                                                "https://auto-drive.pt/wp-content/uploads/2022/03/Edition-55.jpg", 2023,
                                                "O ícone militar de luxo off-road, agora com performance extrema assinada pela AMG.",
                                                Categoria.SUV),

                                luxury("Maybach S 680", "Mercedes-Benz", "S-Class Maybach",
                                                "2900000", "V12 6.0L Bi-Turbo", 612, "900 Nm", 250, "4.5s",
                                                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSOcOqgQaP9rwnrAeO9IrkKU-TRKn0x5PJFiA&s", 2023,
                                                "O ápice do luxo executivo alemão. Bancos traseiros de primeira classe de avião e motor V12.",
                                                Categoria.SEDAN),

                                // LAND ROVER
                                luxury("Range Rover SV LWB", "Land Rover", "Range Rover",
                                                "1600000", "V8 4.4L Bi-Turbo", 615, "750 Nm", 250, "4.6s",
                                                "https://bringatrailer.com/wp-content/uploads/2024/02/2020_land-rover_range-rover-lwb-sv-autobiography_436a5989-71132.jpg?fit=940%2C627", 2024,
                                                "O SUV executivo de luxo britânico por excelência, oferecendo cabine luxuosa de entre-eixos longo.",
                                                Categoria.SUV),

                                // BMW
                                luxury("XM Label Red", "BMW", "XM",
                                                "1500000", "V8 4.4L Bi-Turbo Hybrid", 748, "1000 Nm", 290, "3.8s",
                                                "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiZSgSVQn_vKTuVJKT7vW_Mm0azKPYFd8CLvg9NEnhYvxs5kOluS9MnhOy4xrf1zhdaKSC10WP9R0seKjag519tr4izzBfOQn2nVOfqakJ-TpjdGxB7A6Cg-c4hiVHjbX2dZH_PUDv0hXM98HYM_wbFCV57ctYRdRyOVSESGGhkMMhLqYOa9cOP4ont/s1536/XM%20Red%20Label%201.jpg", 2024,
                                                "O SUV eletrificado mais potente já criado pela divisão M da BMW. Design marcante e agressivo.",
                                                Categoria.SUV),

                                // LEXUS
                                luxury("LFA Nürburgring Edition", "Lexus", "LFA",
                                                "6500000", "V10 4.8L Aspirado", 571, "480 Nm", 325, "3.7s",
                                                "https://www.mouse-motors.com/blobs/Images/Cars/65/f85721af-def3-4564-b56e-424a162cbded.jpg?width=2000&height=1333&format=webp", 2012,
                                                "Um dos carros mais reverenciados da história pelo seu motor V10 que gira a 9.000 rpm, afinado pela Yamaha."),

                                // LOTUS
                                luxury("Evija EV", "Lotus", "Type 130",
                                                "13000000", "4 Motores Elétricos", 2039, "1704 Nm", 350, "2.0s",
                                                "https://wlt-p-001.sitecorecontenthub.cloud/api/public/content/6dab854a31834c73b8880dc0e6610b57?v=fc0c77e9", 2023,
                                                "O primeiro hipercarro totalmente elétrico da Lotus, focado em aerodinâmica e dinâmica de chassi refinada."),

                                // ZENVO
                                luxury("TSR-GT", "Zenvo", "TSR",
                                                "11500000", "V8 5.8L Bi-Supercharged", 1360, "1400 Nm", 424, "2.8s",
                                                "https://zenvoautomotive.com/wp-content/uploads/2023/08/TSR_GT_Front34_L_Web.jpg", 2023,
                                                "Superesportivo dinamarquês focado em velocidades máximas absurdas e aerodinâmica ativa."),

                                // FORD
                                luxury("GT Liquid Carbon", "Ford", "GT",
                                                "4800000", "V6 3.5L EcoBoost Bi-Turbo", 660, "746 Nm", 347, "3.0s",
                                                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQboFZIeL_GepxSrdMqbxcwGJHy8kff0MMBvA&s", 2022,
                                                "Chassi e carroceria inteiros em fibra de carbono aparente, homenageando a lenda de Le Mans."),

                                // PININFARINA
                                luxury("Battista EV", "Pininfarina", "Battista",
                                                "12500000", "4 Motores Elétricos", 1900, "2300 Nm", 350, "1.86s",
                                                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSHF4hqfwE2YHoB78cDDh1ZzDdZfN9ElEHCtA&s", 2023,
                                                "Design escultural clássico italiano aliado a uma motorização puramente elétrica de aceleração recorde."),

                                // HENNESSEY
                                luxury("Venom F5 Coupe", "Hennessey", "Venom F5",
                                                "15000000", "V8 6.6L Twin-Turbo", 1842, "1617 Nm", 500, "2.6s",
                                                "https://www.pmw-magazine.com/wp-content/uploads/2024/09/Hennessey-Venom-F5-M-Roadster-011-1.jpg", 2023,
                                                "Construído no Texas com o único objetivo de ser o hipercarro de combustão mais rápido das Américas."));

                produtoRepository.saveAll(produtos);
        }

        private Produto luxury(String nome, String marca, String modelo, String preco,
                        String motor, int potencia, String torque, int velocidadeMax,
                        String aceleracao, String imagemId, int ano, String descricao) {
                return luxury(nome, marca, modelo, preco, motor, potencia, torque, velocidadeMax,
                                aceleracao, imagemId, ano, descricao, Categoria.ESPORTIVO);
        }

        private Produto luxury(String nome, String marca, String modelo, String preco,
                        String motor, int potencia, String torque, int velocidadeMax,
                        String aceleracao, String imagemId, int ano, String descricao,
                        Categoria categoria) {
            String imagemUrl = imagemId;
                return Produto.builder()
                                .nome(nome)
                                .marca(marca)
                                .modelo(modelo)
                                .preco(new BigDecimal(preco))
                                .descricao(descricao)
                                .imagemUrl(imagemUrl)
                                .ano(ano)
                                .quilometragem(0)
                                .categoria(categoria)
                                .estoque(2)
                                .motor(motor)
                                .potencia(potencia)
                                .torque(torque)
                                .velocidadeMax(velocidadeMax)
                                .aceleracao(aceleracao)
                                .ativo(true)
                                .build();
        }
}
