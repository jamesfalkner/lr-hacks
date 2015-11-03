<%
    /**
     * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
     *
     * This library is free software; you can redistribute it and/or modify it under
     * the terms of the GNU Lesser General Public License as published by the Free
     * Software Foundation; either version 2.1 of the License, or (at your option)
     * any later version.
     *
     * This library is distributed in the hope that it will be useful, but WITHOUT
     * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
     * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
     * details.
     */
%>


<script type="text/javascript">
    var world = {
        "Afghanistan": {"lat": 33.93911, "lng": 67.709953},
        "Albania": {"lat": 41.153332, "lng": 20.168331},
        "Algeria": {"lat": 28.033886, "lng": 1.659626},
        "American Samoa": {"lat": -14.305941, "lng": -170.6962002},
        "Andorra": {"lat": 42.506285, "lng": 1.521801},
        "Angola": {"lat": -11.202692, "lng": 17.873887},
        "Anguilla": {"lat": 18.220554, "lng": -63.06861499999999},
        "Antarctica": {"lat": -82.86275189999999, "lng": -135},
        "Antigua": {"lat": 17.0746557, "lng": -61.8175207},
        "Argentina": {"lat": -38.416097, "lng": -63.61667199999999},
        "Armenia": {"lat": 40.069099, "lng": 45.038189},
        "Aruba": {"lat": 12.52111, "lng": -69.968338},
        "Australian Capital Territory,Australia": {"lat": -35.4734679, "lng": 149.0123679},
        "New South Wales,Australia": {"lat": -31.2532183, "lng": 146.921099},
        "Northern Territory,Australia": {"lat": -19.4914108, "lng": 132.5509603},
        "Queensland,Australia": {"lat": -20.9175738, "lng": 142.7027956},
        "South Australia,Australia": {"lat": -30.0002315, "lng": 136.2091547},
        "Tasmania,Australia": {"lat": -41.3650419, "lng": 146.6284905},
        "Victoria,Australia": {"lat": -37.4713077, "lng": 144.7851531},
        "Western Australia,Australia": {"lat": -27.6728168, "lng": 121.6283098},
        "Austria": {"lat": 47.516231, "lng": 14.550072},
        "Azerbaijan": {"lat": 40.143105, "lng": 47.576927},
        "Bahamas": {"lat": 25.03428, "lng": -77.39627999999999},
        "Bahrain": {"lat": 26.0667, "lng": 50.5577},
        "Bangladesh": {"lat": 23.684994, "lng": 90.356331},
        "Barbados": {"lat": 13.193887, "lng": -59.543198},
        "Belarus": {"lat": 53.709807, "lng": 27.953389},
        "Belgium": {"lat": 50.503887, "lng": 4.469936},
        "Belize": {"lat": 17.189877, "lng": -88.49765},
        "Benin": {"lat": 9.30769, "lng": 2.315834},
        "Bermuda": {"lat": 32.3078, "lng": -64.7505},
        "Bhutan": {"lat": 27.514162, "lng": 90.433601},
        "Bolivia": {"lat": -16.290154, "lng": -63.58865299999999},
        "Bosnia-Herzegovina": {"lat": 43.915886, "lng": 17.679076},
        "Botswana": {"lat": -22.328474, "lng": 24.684866},
        "Brazil": {"lat": -14.235004, "lng": -51.92528},
        "British Virgin Islands": {"lat": 18.420695, "lng": -64.639968},
        "Brunei": {"lat": 4.535277, "lng": 114.727669},
        "Bulgaria": {"lat": 42.733883, "lng": 25.48583},
        "Burkina Faso": {"lat": 12.238333, "lng": -1.561593},
        "Burma (Myanmar)": {"lat": 21.913965, "lng": 95.956223},
        "Burundi": {"lat": -3.373056, "lng": 29.918886},
        "Cambodia": {"lat": 12.565679, "lng": 104.990963},
        "Cameroon": {"lat": 7.369721999999999, "lng": 12.354722},
        "Alberta,Canada": {"lat": 53.9332706, "lng": -116.5765035},
        "British Columbia,Canada": {"lat": 53.7266683, "lng": -127.6476206},
        "Manitoba,Canada": {"lat": 53.7608608, "lng": -98.81387629999999},
        "New Brunswick,Canada": {"lat": 46.5653163, "lng": -66.46191639999999},
        "Newfoundland and Labrador,Canada": {"lat": 53.1355091, "lng": -57.6604364},
        "Northwest Territories,Canada": {"lat": 64.8255441, "lng": -124.8457334},
        "Nova Scotia,Canada": {"lat": 44.68198659999999, "lng": -63.744311},
        "Nunavut,Canada": {"lat": 70.2997711, "lng": -83.1075769},
        "Ontario,Canada": {"lat": 51.253775, "lng": -85.3232139},
        "Prince Edward Island,Canada": {"lat": 46.510712, "lng": -63.41681359999999},
        "Quebec,Canada": {"lat": 52.9399159, "lng": -73.5491361},
        "Saskatchewan,Canada": {"lat": 52.9399159, "lng": -106.4508639},
        "Yukon,Canada": {"lat": 64.2823274, "lng": -135},
        "Cape Verde Island": {"lat": 16, "lng": -24},
        "Cayman Islands": {"lat": 19.3133, "lng": -81.2546},
        "Central African Republic": {"lat": 6.611110999999999, "lng": 20.939444},
        "Chad": {"lat": 15.454166, "lng": 18.732207},
        "Chile": {"lat": -35.675147, "lng": -71.542969},
        "Anhui,China": {"lat": 31.861184, "lng": 117.284923},
        "Aomen,China": {"lat": 33.771159, "lng": 119.7969083},
        "Beijing,China": {"lat": 39.90403, "lng": 116.407526},
        "Chongqing,China": {"lat": 29.56301, "lng": 106.551557},
        "Fujian,China": {"lat": 26.099933, "lng": 119.296506},
        "Gansu,China": {"lat": 36.059421, "lng": 103.826308},
        "Guangdong,China": {"lat": 23.132191, "lng": 113.266531},
        "Guangxi,China": {"lat": 22.815478, "lng": 108.327546},
        "Guizhou,China": {"lat": 26.598194, "lng": 106.70741},
        "Hainan,China": {"lat": 20.017378, "lng": 110.349229},
        "Hebei,China": {"lat": 38.037057, "lng": 114.468665},
        "Heilongjiang,China": {"lat": 45.742347, "lng": 126.661669},
        "Henan,China": {"lat": 34.765527, "lng": 113.753658},
        "Hubei,China": {"lat": 30.545861, "lng": 114.341921},
        "Hunan,China": {"lat": 28.112444, "lng": 112.98381},
        "Jiangsu,China": {"lat": 32.061707, "lng": 118.763232},
        "Jiangxi,China": {"lat": 28.674425, "lng": 115.909137},
        "Jilin,China": {"lat": 43.896082, "lng": 125.326065},
        "Liaoning,China": {"lat": 41.835441, "lng": 123.42944},
        "Nei Mongol,China": {"lat": 40.817498, "lng": 111.765618},
        "Ningxia,China": {"lat": 38.471318, "lng": 106.258754},
        "Qinghai,China": {"lat": 36.620901, "lng": 101.780199},
        "Shaanxi,China": {"lat": 34.265472, "lng": 108.954239},
        "Shandong,China": {"lat": 36.668627, "lng": 117.020411},
        "Shanghai,China": {"lat": 31.230393, "lng": 121.473704},
        "Shanxi,China": {"lat": 37.873532, "lng": 112.562398},
        "Sichuan,China": {"lat": 30.651652, "lng": 104.075931},
        "Taiwan,China": {"lat": 23.69781, "lng": 120.960515},
        "Tianjin,China": {"lat": 39.084158, "lng": 117.200983},
        "Xianggang,China": {"lat": 36.09602280000001, "lng": 120.4715346},
        "Xinjiang,China": {"lat": 43.793028, "lng": 87.62781199999999},
        "Xizang,China": {"lat": 29.646923, "lng": 91.117212},
        "Yunnan,China": {"lat": 25.04535, "lng": 102.709938},
        "Zhejiang,China": {"lat": 30.267447, "lng": 120.152792},
        "Christmas Island": {"lat": -10.447525, "lng": 105.690449},
        "Cocos Islands": {"lat": -12.1707796, "lng": 96.84173919999999},
        "Colombia": {"lat": 4.570868, "lng": -74.297333},
        "Comoros": {"lat": -11.6455, "lng": 43.3333},
        "Cook Islands": {"lat": -21.236736, "lng": -159.777671},
        "Costa Rica": {"lat": 9.748916999999999, "lng": -83.753428},
        "Croatia": {"lat": 45.1, "lng": 15.2},
        "Cuba": {"lat": 21.521757, "lng": -77.781167},
        "Cyprus": {"lat": 35.126413, "lng": 33.429859},
        "Czech Republic": {"lat": 49.81749199999999, "lng": 15.472962},
        "Democratic Republic of Congo": {"lat": 38.90544850000001, "lng": -77.0393167},
        "Denmark": {"lat": 56.26392, "lng": 9.501785},
        "Djibouti": {"lat": 11.825138, "lng": 42.590275},
        "Dominica": {"lat": 15.414999, "lng": -61.37097600000001},
        "Dominican Republic": {"lat": 18.735693, "lng": -70.162651},
        "Ecuador": {"lat": -1.831239, "lng": -78.18340599999999},
        "Egypt": {"lat": 26.820553, "lng": 30.802498},
        "El Salvador": {"lat": 13.794185, "lng": -88.89653},
        "Equatorial Guinea": {"lat": 1.650801, "lng": 10.267895},
        "Eritrea": {"lat": 15.179384, "lng": 39.782334},
        "Estonia": {"lat": 58.595272, "lng": 25.013607},
        "Ethiopia": {"lat": 9.145000000000001, "lng": 40.489673},
        "Faeroe Islands": {"lat": 61.89263500000001, "lng": -6.911805999999999},
        "Falkland Islands": {"lat": -51.796253, "lng": -59.523613},
        "Fiji Islands": {"lat": -18, "lng": 178},
        "Finland": {"lat": 61.92410999999999, "lng": 25.748151},
        "Alsace,France": {"lat": 48.3181795, "lng": 7.441624099999999},
        "Aquitaine,France": {"lat": 44.7002222, "lng": -0.2995785},
        "Auvergne,France": {"lat": 45.7032695, "lng": 3.3448536},
        "Basse-Normandie,France": {"lat": 48.8788472, "lng": -0.5157492},
        "Bourgogne,France": {"lat": 47.0525047, "lng": 4.3837215},
        "Bretagne,France": {"lat": 48.2020471, "lng": -2.9326435},
        "Centre,France": {"lat": 47.7515686, "lng": 1.6750631},
        "Champagne-Ardenne,France": {"lat": 48.7934092, "lng": 4.4725249},
        "Corse,France": {"lat": 42.0396042, "lng": 9.012892599999999},
        "Guadeloupe,France": {"lat": 45.108189, "lng": 5.362909},
        "Guyane,France": {"lat": 5.1660547, "lng": -52.6441492},
        "Haute-Normandie,France": {"lat": 49.524641, "lng": 0.8828328999999999},
        "Languedoc-Roussillon,France": {"lat": 43.5912356, "lng": 3.2583626},
        "Limousin,France": {"lat": 45.8932231, "lng": 1.5696018},
        "Lorraine,France": {"lat": 48.8744233, "lng": 6.2080932},
        "Martinique,France": {"lat": 45.0202232, "lng": 2.0860248},
        "Nord Pas de Calais,France": {"lat": 50.48011529999999, "lng": 2.7937265},
        "Pays de la Loire,France": {"lat": 47.7632836, "lng": -0.3299687},
        "Picardie,France": {"lat": 49.66361269999999, "lng": 2.5280732},
        "Poitou-Charentes,France": {"lat": 45.903552, "lng": -0.3091837},
        "French Guiana": {"lat": 3.933889, "lng": -53.125782},
        "French Polynesia": {"lat": -17.679742, "lng": -149.406843},
        "Gabon": {"lat": -0.803689, "lng": 11.609444},
        "Gambia": {"lat": 13.443182, "lng": -15.310139},
        "Georgia": {"lat": 32.1574351, "lng": -82.90712300000001},
        "Bayern,Germany": {"lat": 48.7904472, "lng": 11.4978895},
        "Berlin,Germany": {"lat": 52.519171, "lng": 13.4060912},
        "Brandenburg,Germany": {"lat": 52.1313922, "lng": 13.2162494},
        "Bremen,Germany": {"lat": 53.07929619999999, "lng": 8.8016937},
        "Hamburg,Germany": {"lat": 53.5510846, "lng": 9.9936818},
        "Hessen,Germany": {"lat": 50.6520515, "lng": 9.162437599999999},
        "Mecklenburg-Vorpommern,Germany": {"lat": 53.6126505, "lng": 12.4295953},
        "Niedersachsen,Germany": {"lat": 52.6367036, "lng": 9.8450765},
        "Nordrhein-Westfalen,Germany": {"lat": 51.43323669999999, "lng": 7.661593799999999},
        "Rheinland-Pfalz,Germany": {"lat": 50.118346, "lng": 7.3089527},
        "Saarland,Germany": {"lat": 49.3964234, "lng": 7.0229607},
        "Sachsen,Germany": {"lat": 51.1045407, "lng": 13.2017384},
        "Sachsen-Anhalt,Germany": {"lat": 51.9502649, "lng": 11.6922735},
        "Schleswig-Holstein,Germany": {"lat": 54.21936720000001, "lng": 9.696116700000001},
        "Ghana": {"lat": 7.946527, "lng": -1.023194},
        "Gibraltar": {"lat": 36.140751, "lng": -5.353585},
        "Greece": {"lat": 39.074208, "lng": 21.824312},
        "Greenland": {"lat": 71.706936, "lng": -42.604303},
        "Grenada": {"lat": 12.1165, "lng": -61.67899999999999},
        "Guadeloupe": {"lat": 16.265, "lng": -61.55099999999999},
        "Guam": {"lat": 13.444304, "lng": 144.793731},
        "Guatemala": {"lat": 15.783471, "lng": -90.23075899999999},
        "Guinea": {"lat": 9.945587, "lng": -9.696645},
        "Guinea-Bissau": {"lat": 11.803749, "lng": -15.180413},
        "Guyana": {"lat": 4.860416, "lng": -58.93018},
        "Haiti": {"lat": 18.971187, "lng": -72.285215},
        "Honduras": {"lat": 15.199999, "lng": -86.241905},
        "Hong Kong": {"lat": 22.396428, "lng": 114.109497},
        "Hungary": {"lat": 47.162494, "lng": 19.503304},
        "Iceland": {"lat": 64.963051, "lng": -19.020835},
        "India": {"lat": 20.593684, "lng": 78.96288},
        "Indonesia": {"lat": -0.789275, "lng": 113.921327},
        "Iran": {"lat": 32.427908, "lng": 53.688046},
        "Iraq": {"lat": 33.223191, "lng": 43.679291},
        "Ireland": {"lat": 53.41291, "lng": -8.24389},
        "Israel": {"lat": 31.046051, "lng": 34.851612},
        "Agrigento,Italy": {"lat": 37.3110897, "lng": 13.5765475},
        "Alessandria,Italy": {"lat": 44.9072727, "lng": 8.6116796},
        "Ancona,Italy": {"lat": 43.6158299, "lng": 13.518915},
        "Aosta,Italy": {"lat": 45.7349551, "lng": 7.313076199999999},
        "Arezzo,Italy": {"lat": 43.4669678, "lng": 11.8823076},
        "Ascoli Piceno,Italy": {"lat": 42.8536043, "lng": 13.5749442},
        "Asti,Italy": {"lat": 44.90075119999999, "lng": 8.2064257},
        "Avellino,Italy": {"lat": 40.9143842, "lng": 14.7902801},
        "Bari,Italy": {"lat": 41.1171432, "lng": 16.8718715},
        "Barletta-Andria-Trani,Italy": {"lat": 41.2004543, "lng": 16.2051484},
        "Belluno,Italy": {"lat": 46.1424635, "lng": 12.2167088},
        "Benevento,Italy": {"lat": 41.1297613, "lng": 14.7826208},
        "Bergamo,Italy": {"lat": 45.6982642, "lng": 9.6772698},
        "Biella,Italy": {"lat": 45.56288420000001, "lng": 8.0583397},
        "Bologna,Italy": {"lat": 44.494887, "lng": 11.3426163},
        "Bolzano,Italy": {"lat": 46.4982953, "lng": 11.3547582},
        "Brescia,Italy": {"lat": 45.5411875, "lng": 10.2194437},
        "Brindisi,Italy": {"lat": 40.6327278, "lng": 17.9417616},
        "Cagliari,Italy": {"lat": 39.2238411, "lng": 9.1216613},
        "Caltanissetta,Italy": {"lat": 37.4901115, "lng": 14.0628928},
        "Campobasso,Italy": {"lat": 41.5602544, "lng": 14.6627161},
        "Carbonia-Iglesias,Italy": {"lat": 39.25346589999999, "lng": 8.5721016},
        "Caserta,Italy": {"lat": 41.0723484, "lng": 14.3311337},
        "Catania,Italy": {"lat": 37.5080386, "lng": 15.0828512},
        "Catanzaro,Italy": {"lat": 38.90979189999999, "lng": 16.5876516},
        "Chieti,Italy": {"lat": 42.347886, "lng": 14.1635845},
        "Como,Italy": {"lat": 45.8080597, "lng": 9.0851765},
        "Cosenza,Italy": {"lat": 39.2982629, "lng": 16.2537357},
        "Cremona,Italy": {"lat": 45.133249, "lng": 10.0226511},
        "Crotone,Italy": {"lat": 39.0807932, "lng": 17.1271102},
        "Cuneo,Italy": {"lat": 44.3844766, "lng": 7.5426711},
        "Enna,Italy": {"lat": 37.5655912, "lng": 14.2751277},
        "Fermo,Italy": {"lat": 43.162818, "lng": 13.71832},
        "Ferrara,Italy": {"lat": 44.8381237, "lng": 11.619787},
        "Firenze,Italy": {"lat": 43.7710332, "lng": 11.2480006},
        "Foggia,Italy": {"lat": 41.4621984, "lng": 15.5446302},
        "Forli-Cesena,Italy": {"lat": 44.1396438, "lng": 12.2464292},
        "Frosinone,Italy": {"lat": 41.6396009, "lng": 13.3426341},
        "Genova,Italy": {"lat": 44.4056499, "lng": 8.946256},
        "Gorizia,Italy": {"lat": 45.9401812, "lng": 13.6201754},
        "Grosseto,Italy": {"lat": 42.7635254, "lng": 11.1123634},
        "Imperia,Italy": {"lat": 43.8905684, "lng": 8.0346654},
        "Isernia,Italy": {"lat": 41.5960411, "lng": 14.2331612},
        "L'Aquila,Italy": {"lat": 42.3498479, "lng": 13.3995091},
        "La Spezia,Italy": {"lat": 44.1024504, "lng": 9.824082599999999},
        "Latina,Italy": {"lat": 41.4675671, "lng": 12.9035965},
        "Lecce,Italy": {"lat": 40.3515155, "lng": 18.1750161},
        "Lecco,Italy": {"lat": 45.8565698, "lng": 9.397670399999999},
        "Livorno,Italy": {"lat": 43.548473, "lng": 10.3105674},
        "Lodi,Italy": {"lat": 45.3138041, "lng": 9.5018274},
        "Lucca,Italy": {"lat": 43.8376211, "lng": 10.4950609},
        "Macerata,Italy": {"lat": 43.2984268, "lng": 13.4534767},
        "Mantova,Italy": {"lat": 45.1564168, "lng": 10.7913751},
        "Massa-Carrara,Italy": {"lat": 44.0793245, "lng": 10.097677},
        "Matera,Italy": {"lat": 40.666379, "lng": 16.6043199},
        "Medio Campidano,Italy": {"lat": 39.5317389, "lng": 8.704075},
        "Messina,Italy": {"lat": 38.1938137, "lng": 15.5540152},
        "Milano,Italy": {"lat": 45.4654542, "lng": 9.186516},
        "Modena,Italy": {"lat": 44.6488366, "lng": 10.9200867},
        "Monza,Italy": {"lat": 44.9911525, "lng": 7.8046158},
        "Napoli,Italy": {"lat": 40.8517746, "lng": 14.2681244},
        "Novara,Italy": {"lat": 45.44692999999999, "lng": 8.622161199999999},
        "Nuoro,Italy": {"lat": 40.32023179999999, "lng": 9.3250467},
        "Ogliastra,Italy": {"lat": 39.8410536, "lng": 9.456155},
        "Olbia-Tempio,Italy": {"lat": 40.8268383, "lng": 9.2785583},
        "Oristano,Italy": {"lat": 39.906182, "lng": 8.5883863},
        "Padova,Italy": {"lat": 45.4064349, "lng": 11.8767611},
        "Palermo,Italy": {"lat": 38.1156879, "lng": 13.3612671},
        "Parma,Italy": {"lat": 44.801485, "lng": 10.3279036},
        "Pavia,Italy": {"lat": 45.1847248, "lng": 9.1582069},
        "Perugia,Italy": {"lat": 43.1107168, "lng": 12.3908279},
        "Pesaro e Urbino,Italy": {"lat": 43.6130118, "lng": 12.7135121},
        "Pescara,Italy": {"lat": 42.4617902, "lng": 14.2160898},
        "Piacenza,Italy": {"lat": 45.0473754, "lng": 9.6865813},
        "Pisa,Italy": {"lat": 43.7228386, "lng": 10.4016888},
        "Pistoia,Italy": {"lat": 43.9303475, "lng": 10.9078587},
        "Pordenone,Italy": {"lat": 45.96263980000001, "lng": 12.6551362},
        "Potenza,Italy": {"lat": 40.6404067, "lng": 15.8056041},
        "Prato,Italy": {"lat": 43.8777049, "lng": 11.102228},
        "Ragusa,Italy": {"lat": 36.9269273, "lng": 14.7255129},
        "Ravenna,Italy": {"lat": 44.4183598, "lng": 12.2035294},
        "Reggio Calabria,Italy": {"lat": 38.1113006, "lng": 15.6472914},
        "Reggio Emilia,Italy": {"lat": 44.6989932, "lng": 10.6296859},
        "Rieti,Italy": {"lat": 42.404509, "lng": 12.8567281},
        "Rimini,Italy": {"lat": 44.026458, "lng": 12.5527505},
        "Roma,Italy": {"lat": 41.8929163, "lng": 12.4825199},
        "Rovigo,Italy": {"lat": 45.0698118, "lng": 11.7902158},
        "Salerno,Italy": {"lat": 40.68244079999999, "lng": 14.7680961},
        "Sassari,Italy": {"lat": 40.7259269, "lng": 8.5556826},
        "Savona,Italy": {"lat": 44.2975603, "lng": 8.4645},
        "Siena,Italy": {"lat": 43.31971, "lng": 11.3658004},
        "Siracusa,Italy": {"lat": 37.0754739, "lng": 15.2865861},
        "Sondrio,Italy": {"lat": 46.1698583, "lng": 9.8787674},
        "Taranto,Italy": {"lat": 40.4657939, "lng": 17.2477784},
        "Teramo,Italy": {"lat": 42.6611431, "lng": 13.6986639},
        "Terni,Italy": {"lat": 42.5636168, "lng": 12.6426604},
        "Torino,Italy": {"lat": 45.07098200000001, "lng": 7.685676},
        "Trapani,Italy": {"lat": 38.0176177, "lng": 12.537202},
        "Trento,Italy": {"lat": 46.0696924, "lng": 11.1210886},
        "Treviso,Italy": {"lat": 45.6669011, "lng": 12.243039},
        "Trieste,Italy": {"lat": 45.6495354, "lng": 13.7779716},
        "Udine,Italy": {"lat": 46.0710668, "lng": 13.2345794},
        "Varese,Italy": {"lat": 45.82059890000001, "lng": 8.8250576},
        "Venezia,Italy": {"lat": 45.4408474, "lng": 12.3155151},
        "Verbano-Cusio-Ossola,Italy": {"lat": 46.1399688, "lng": 8.2724649},
        "Vercelli,Italy": {"lat": 45.32022720000001, "lng": 8.418573499999999},
        "Verona,Italy": {"lat": 45.4383563, "lng": 10.9916578},
        "Vibo Valentia,Italy": {"lat": 38.6757774, "lng": 16.0983488},
        "Vicenza,Italy": {"lat": 45.5454787, "lng": 11.5354214},
        "Viterbo,Italy": {"lat": 42.4206766, "lng": 12.107669},
        "Ivory Coast": {"lat": 7.539988999999999, "lng": -5.547079999999999},
        "Jamaica": {"lat": 18.109581, "lng": -77.297508},
        "Japan": {"lat": 36.204824, "lng": 138.252924},
        "Jordan": {"lat": 30.585164, "lng": 36.238414},
        "Kazakhstan": {"lat": 48.019573, "lng": 66.923684},
        "Kenya": {"lat": -0.023559, "lng": 37.906193},
        "Kiribati": {"lat": 1.8668577, "lng": -157.3599202},
        "Kuwait": {"lat": 29.31166, "lng": 47.481766},
        "Kyrgyzstan": {"lat": 41.20438, "lng": 74.766098},
        "Laos": {"lat": 19.85627, "lng": 102.495496},
        "Latvia": {"lat": 56.879635, "lng": 24.603189},
        "Lebanon": {"lat": 33.854721, "lng": 35.862285},
        "Lesotho": {"lat": -29.609988, "lng": 28.233608},
        "Liberia": {"lat": 6.428055, "lng": -9.429499000000002},
        "Libya": {"lat": 26.3351, "lng": 17.228331},
        "Liechtenstein": {"lat": 47.166, "lng": 9.555373},
        "Lithuania": {"lat": 55.169438, "lng": 23.881275},
        "Luxembourg": {"lat": 49.815273, "lng": 6.129582999999999},
        "Macau": {"lat": 22.198745, "lng": 113.543873},
        "Macedonia": {"lat": 41.608635, "lng": 21.745275},
        "Madagascar": {"lat": -18.766947, "lng": 46.869107},
        "Malawi": {"lat": -13.254308, "lng": 34.301525},
        "Malaysia": {"lat": 4.210484, "lng": 101.975766},
        "Maldives": {"lat": 1.9772469, "lng": 73.5361035},
        "Mali": {"lat": 17.570692, "lng": -3.996166},
        "Malta": {"lat": 35.937496, "lng": 14.375416},
        "Marshall Islands": {"lat": 7.131474, "lng": 171.184478},
        "Martinique": {"lat": 14.641528, "lng": -61.024174},
        "Mauritania": {"lat": 21.00789, "lng": -10.940835},
        "Mauritius": {"lat": -20.348404, "lng": 57.55215200000001},
        "Mayotte Island": {"lat": 51.8830514, "lng": -8.4418426},
        "Aguascalientes,Mexico": {"lat": 21.8817964, "lng": -102.2912701},
        "Baja California,Mexico": {"lat": 30.8406338, "lng": -115.2837585},
        "Baja California Sur,Mexico": {"lat": 26.0444446, "lng": -111.6660725},
        "Campeche,Mexico": {"lat": 18.931225, "lng": -90.2618068},
        "Chiapas,Mexico": {"lat": 16.7569318, "lng": -93.12923529999999},
        "Chihuahua,Mexico": {"lat": 28.630581, "lng": -106.0737},
        "Coahuila,Mexico": {"lat": 27.058676, "lng": -101.7068294},
        "Colima,Mexico": {"lat": 19.245226, "lng": -103.733455},
        "Durango,Mexico": {"lat": 24.0277202, "lng": -104.6531759},
        "Guanajuato,Mexico": {"lat": 21.018111, "lng": -101.25832},
        "Guerrero,Mexico": {"lat": 17.4391926, "lng": -99.54509739999999},
        "Hidalgo,Mexico": {"lat": 20.6668191, "lng": -99.0128926},
        "Jalisco,Mexico": {"lat": 20.1540195, "lng": -103.914399},
        "Mexico,Mexico": {"lat": 19.4326077, "lng": -99.133208},
        "Michoacan,Mexico": {"lat": 19.5665192, "lng": -101.7068294},
        "Morelos,Mexico": {"lat": 18.6813049, "lng": -99.1013498},
        "Nayarit,Mexico": {"lat": 21.7513844, "lng": -104.8454619},
        "Nuevo Leon,Mexico": {"lat": 25.7276624, "lng": -99.54509739999999},
        "Oaxaca,Mexico": {"lat": 17.0669444, "lng": -96.7202778},
        "Puebla,Mexico": {"lat": 19.046795, "lng": -98.209223},
        "Queretaro,Mexico": {"lat": 20.5887932, "lng": -100.3898881},
        "Quintana Roo,Mexico": {"lat": 19.1817393, "lng": -88.4791376},
        "San Luis PotosÃÂ­,Mexico": {"lat": 38.6270025, "lng": -90.19940419999999},
        "Sinaloa,Mexico": {"lat": 25.1721091, "lng": -107.4795173},
        "Sonora,Mexico": {"lat": 29.2972247, "lng": -110.3308814},
        "Tabasco,Mexico": {"lat": 21.8666667, "lng": -102.9166667},
        "Tamaulipas,Mexico": {"lat": 24.26694, "lng": -98.8362755},
        "Tlaxcala,Mexico": {"lat": 19.3, "lng": -98.24},
        "Veracruz,Mexico": {"lat": 19.1902778, "lng": -96.1533333},
        "Yucatan,Mexico": {"lat": 20.7098786, "lng": -89.0943377},
        "Zacatecas,Mexico": {"lat": 22.7708555, "lng": -102.5832426},
        "Micronesia": {"lat": 6.8874574, "lng": 158.2150717},
        "Moldova": {"lat": 47.411631, "lng": 28.369885},
        "Monaco": {"lat": 43.73841760000001, "lng": 7.424615799999999},
        "Mongolia": {"lat": 46.862496, "lng": 103.846656},
        "Montenegro": {"lat": 42.708678, "lng": 19.37439},
        "Montserrat": {"lat": 16.742498, "lng": -62.187366},
        "Morocco": {"lat": 31.791702, "lng": -7.092619999999999},
        "Mozambique": {"lat": -18.665695, "lng": 35.529562},
        "Namibia": {"lat": -22.95764, "lng": 18.49041},
        "Nauru": {"lat": -0.522778, "lng": 166.931503},
        "Nepal": {"lat": 28.394857, "lng": 84.12400799999999},
        "Drenthe,Netherlands": {"lat": 52.9476012, "lng": 6.623058599999999},
        "Flevoland,Netherlands": {"lat": 52.5279781, "lng": 5.595350799999999},
        "Friesland,Netherlands": {"lat": 53.1641642, "lng": 5.7817542},
        "Gelderland,Netherlands": {"lat": 52.045155, "lng": 5.871823399999999},
        "Groningen,Netherlands": {"lat": 53.219231, "lng": 6.575369999999999},
        "Limburg,Netherlands": {"lat": 51.4427238, "lng": 6.0608726},
        "Noord-Brabant,Netherlands": {"lat": 51.4826537, "lng": 5.2321687},
        "Noord-Holland,Netherlands": {"lat": 52.5205869, "lng": 4.788474},
        "Overijssel,Netherlands": {"lat": 52.4387814, "lng": 6.5016411},
        "Utrecht,Netherlands": {"lat": 52.09179, "lng": 5.114569899999999},
        "Zeeland,Netherlands": {"lat": 51.4940309, "lng": 3.8496815},
        "Zuid-Holland,Netherlands": {"lat": 52.0207975, "lng": 4.4937836},
        "Netherlands Antilles": {"lat": 12.2248767, "lng": -68.81088489999999},
        "New Caledonia": {"lat": -20.904305, "lng": 165.618042},
        "New Zealand": {"lat": -40.900557, "lng": 174.885971},
        "Nicaragua": {"lat": 12.865416, "lng": -85.207229},
        "Niger": {"lat": 17.607789, "lng": 8.081666},
        "Nigeria": {"lat": 9.081999, "lng": 8.675277},
        "Niue": {"lat": -19.054445, "lng": -169.867233},
        "Norfolk Island": {"lat": -29.040835, "lng": 167.954712},
        "North Korea": {"lat": 40.339852, "lng": 127.510093},
        "Akershus,Norway": {"lat": 60.00002019999999, "lng": 11.3690301},
        "Aust-Agder,Norway": {"lat": 58.66703039999999, "lng": 8.0844752},
        "Buskerud,Norway": {"lat": 60.48460249999999, "lng": 8.698376399999999},
        "Finnmark,Norway": {"lat": 70.4830388, "lng": 26.0135108},
        "Hedmark,Norway": {"lat": 61.3967311, "lng": 11.5627369},
        "Hordaland,Norway": {"lat": 60.2733674, "lng": 5.7220194},
        "Nordland,Norway": {"lat": 67.097529, "lng": 14.5736287},
        "Oppland,Norway": {"lat": 61.5422752, "lng": 9.716631399999999},
        "Oslo,Norway": {"lat": 59.9138688, "lng": 10.7522454},
        "Rogaland,Norway": {"lat": 59.1489544, "lng": 6.0143431},
        "Sogn og Fjordane,Norway": {"lat": 61.5539435, "lng": 6.3325879},
        "Telemark,Norway": {"lat": 59.39139849999999, "lng": 8.321121},
        "Troms,Norway": {"lat": 69.81782419999999, "lng": 18.7819365},
        "Vest-Agder,Norway": {"lat": 58.368605, "lng": 6.901961600000001},
        "Vestfold,Norway": {"lat": 59.17078619999999, "lng": 10.1144355},
        "Oman": {"lat": 21.512583, "lng": 55.923255},
        "Pakistan": {"lat": 30.375321, "lng": 69.34511599999999},
        "Palau": {"lat": 7.514979999999999, "lng": 134.58252},
        "Palestine": {"lat": 31.952162, "lng": 35.233154},
        "Panama": {"lat": 8.537981, "lng": -80.782127},
        "Papua New Guinea": {"lat": -6.314992999999999, "lng": 143.95555},
        "Paraguay": {"lat": -23.442503, "lng": -58.443832},
        "Peru": {"lat": -9.189967, "lng": -75.015152},
        "Philippines": {"lat": 12.879721, "lng": 121.774017},
        "Poland": {"lat": 51.919438, "lng": 19.145136},
        "Portugal": {"lat": 39.39987199999999, "lng": -8.224454},
        "Puerto Rico": {"lat": 18.220833, "lng": -66.590149},
        "Qatar": {"lat": 25.354826, "lng": 51.183884},
        "Republic of Congo": {"lat": -0.228021, "lng": 15.827659},
        "Reunion Island": {"lat": -21.115141, "lng": 55.536384},
        "Romania": {"lat": 45.943161, "lng": 24.96676},
        "Russia": {"lat": 61.52401, "lng": 105.318756},
        "Rwanda": {"lat": -1.940278, "lng": 29.873888},
        "San Marino": {"lat": 43.94236, "lng": 12.457777},
        "Sao Tome & Principe": {"lat": 0.18636, "lng": 6.613080999999999},
        "Saudi Arabia": {"lat": 23.885942, "lng": 45.079162},
        "Senegal": {"lat": 14.497401, "lng": -14.452362},
        "Serbia": {"lat": 44.016521, "lng": 21.005859},
        "Seychelles": {"lat": -4.679574, "lng": 55.491977},
        "Sierra Leone": {"lat": 8.460555, "lng": -11.779889},
        "Singapore": {"lat": 1.352083, "lng": 103.819836},
        "Slovakia": {"lat": 48.669026, "lng": 19.699024},
        "Slovenia": {"lat": 46.151241, "lng": 14.995463},
        "Solomon Islands": {"lat": -9.64571, "lng": 160.156194},
        "Somalia": {"lat": 5.152149, "lng": 46.199616},
        "South Africa": {"lat": -30.559482, "lng": 22.937506},
        "South Korea": {"lat": 35.907757, "lng": 127.766922},
        "Andalusia,Spain": {"lat": 37.5442706, "lng": -4.7277528},
        "Aragon,Spain": {"lat": 41.5976275, "lng": -0.9056623},
        "Asturias,Spain": {"lat": 43.2504393, "lng": -5.983257699999999},
        "Balearic Islands,Spain": {"lat": 39.5341789, "lng": 2.8577105},
        "Basque Country,Spain": {"lat": 42.9896248, "lng": -2.6189273},
        "Canary Islands,Spain": {"lat": 28.2915637, "lng": -16.6291304},
        "Cantabria,Spain": {"lat": 43.1828396, "lng": -3.9878427},
        "Castile and Leon,Spain": {"lat": 41.83568210000001, "lng": -4.397635699999999},
        "Castile-La Mancha,Spain": {"lat": 39.2795607, "lng": -3.097702},
        "Catalonia,Spain": {"lat": 41.5911589, "lng": 1.5208624},
        "Ceuta,Spain": {"lat": 35.8883838, "lng": -5.324635600000001},
        "Extremadura,Spain": {"lat": 39.4937392, "lng": -6.0679194},
        "Galicia,Spain": {"lat": 42.5750554, "lng": -8.1338558},
        "La Rioja,Spain": {"lat": 42.2870733, "lng": -2.539603},
        "Madrid,Spain": {"lat": 40.4167754, "lng": -3.7037902},
        "Melilla,Spain": {"lat": 35.2922775, "lng": -2.9380973},
        "Murcia,Spain": {"lat": 37.992331, "lng": -1.1304575},
        "Navarra,Spain": {"lat": 42.6953909, "lng": -1.6760691},
        "Valencia,Spain": {"lat": 39.4699075, "lng": -0.3762881},
        "Sri Lanka": {"lat": 7.873053999999999, "lng": 80.77179699999999},
        "St. Helena": {"lat": 38.5027778, "lng": -122.4697222},
        "St. Kitts": {"lat": 17.3433796, "lng": -62.7559043},
        "St. Lucia": {"lat": 13.909444, "lng": -60.978893},
        "St. Pierre & Miquelon": {"lat": 46.8852, "lng": -56.3159},
        "St. Vincent": {"lat": 13.2648773, "lng": -61.21024420000001},
        "Sudan": {"lat": 12.862807, "lng": 30.217636},
        "Suriname": {"lat": 3.919305, "lng": -56.027783},
        "Swaziland": {"lat": -26.522503, "lng": 31.465866},
        "Sweden": {"lat": 60.12816100000001, "lng": 18.643501},
        "Aargau,Switzerland": {"lat": 47.3876664, "lng": 8.2554295},
        "Appenzell Ausserrhoden,Switzerland": {"lat": 47.366481, "lng": 9.3000916},
        "Appenzell Innerrhoden,Switzerland": {"lat": 47.3161925, "lng": 9.4316573},
        "Basel-Landschaft,Switzerland": {"lat": 47.44181220000001, "lng": 7.7644002},
        "Basel-Stadt,Switzerland": {"lat": 47.557421, "lng": 7.592572699999999},
        "Bern,Switzerland": {"lat": 46.9479222, "lng": 7.444608499999999},
        "Fribourg,Switzerland": {"lat": 46.8016663, "lng": 7.145568300000001},
        "Geneva,Switzerland": {"lat": 46.1983922, "lng": 6.142296099999999},
        "Glarus,Switzerland": {"lat": 47.0404265, "lng": 9.0672085},
        "Jura,Switzerland": {"lat": 47.3444474, "lng": 7.143060800000001},
        "Lucerne,Switzerland": {"lat": 47.05033479999999, "lng": 8.3156129},
        "Nidwalden,Switzerland": {"lat": 46.9267016, "lng": 8.3849982},
        "Obwalden,Switzerland": {"lat": 46.877858, "lng": 8.251249},
        "Schaffhausen,Switzerland": {"lat": 47.6969, "lng": 8.63569},
        "Schwyz,Switzerland": {"lat": 47.0198346, "lng": 8.647397699999999},
        "Solothurn,Switzerland": {"lat": 47.2086574, "lng": 7.5379549},
        "St. Gallen,Switzerland": {"lat": 47.4166667, "lng": 9.3666667},
        "Thurgau,Switzerland": {"lat": 47.60378559999999, "lng": 9.0557371},
        "Ticino,Switzerland": {"lat": 46.331734, "lng": 8.800452900000002},
        "Uri,Switzerland": {"lat": 46.7738629, "lng": 8.602515300000002},
        "Valais,Switzerland": {"lat": 46.1904614, "lng": 7.5449226},
        "Vaud,Switzerland": {"lat": 46.5613135, "lng": 6.536765},
        "Zug,Switzerland": {"lat": 47.168, "lng": 8.51648},
        "Syria": {"lat": 34.80207499999999, "lng": 38.996815},
        "Taiwan": {"lat": 23.69781, "lng": 120.960515},
        "Tajikistan": {"lat": 38.861034, "lng": 71.276093},
        "Tanzania": {"lat": -6.369028, "lng": 34.888822},
        "Thailand": {"lat": 15.870032, "lng": 100.992541},
        "Togo": {"lat": 8.619543, "lng": 0.824782},
        "Tonga": {"lat": -21.178986, "lng": -175.198242},
        "Trinidad & Tobago": {"lat": 10.691803, "lng": -61.222503},
        "Tunisia": {"lat": 33.886917, "lng": 9.537499},
        "Turkey": {"lat": 38.963745, "lng": 35.243322},
        "Turkmenistan": {"lat": 38.969719, "lng": 59.556278},
        "Turks & Caicos": {"lat": 43.7045454, "lng": -79.3666866},
        "Tuvalu": {"lat": -10.7280717, "lng": 179.4726562},
        "Uganda": {"lat": 1.373333, "lng": 32.290275},
        "Ukraine": {"lat": 48.379433, "lng": 31.16558},
        "United Arab Emirates": {"lat": 23.424076, "lng": 53.847818},
        "United Kingdom": {"lat": 55.378051, "lng": -3.435973},
        "Alabama,United States": {"lat": 32.3182314, "lng": -86.902298},
        "Alaska,United States": {"lat": 64.2008413, "lng": -149.4936733},
        "Arizona,United States": {"lat": 34.0489281, "lng": -111.0937311},
        "Arkansas,United States": {"lat": 35.20105, "lng": -91.8318334},
        "California,United States": {"lat": 36.778261, "lng": -119.4179324},
        "Colorado,United States": {"lat": 39.5500507, "lng": -105.7820674},
        "Connecticut,United States": {"lat": 41.6032207, "lng": -73.087749},
        "Delaware,United States": {"lat": 38.9108325, "lng": -75.52766989999999},
        "District of Columbia,United States": {"lat": 38.8951118, "lng": -77.0363658},
        "Florida,United States": {"lat": 27.6648274, "lng": -81.5157535},
        "Georgia,United States": {"lat": 32.1574351, "lng": -82.90712300000001},
        "Hawaii,United States": {"lat": 19.8967662, "lng": -155.5827818},
        "Idaho,United States": {"lat": 44.0682019, "lng": -114.7420408},
        "Illinois,United States": {"lat": 40.6331249, "lng": -89.3985283},
        "Indiana,United States": {"lat": 40.2671941, "lng": -86.1349019},
        "Iowa,United States": {"lat": 41.8780025, "lng": -93.097702},
        "Kansas,United States": {"lat": 39.011902, "lng": -98.4842465},
        "Kentucky ,United States": {"lat": 37.8393332, "lng": -84.2700179},
        "Louisiana ,United States": {"lat": 31.2448234, "lng": -92.14502449999999},
        "Maine,United States": {"lat": 45.253783, "lng": -69.4454689},
        "Maryland,United States": {"lat": 39.0457549, "lng": -76.64127119999999},
        "Massachusetts,United States": {"lat": 42.4072107, "lng": -71.3824374},
        "Michigan,United States": {"lat": 44.3148443, "lng": -85.60236429999999},
        "Minnesota,United States": {"lat": 46.729553, "lng": -94.6858998},
        "Mississippi,United States": {"lat": 32.3546679, "lng": -89.3985283},
        "Missouri,United States": {"lat": 37.9642529, "lng": -91.8318334},
        "Montana,United States": {"lat": 46.8796822, "lng": -110.3625658},
        "Nebraska,United States": {"lat": 41.4925374, "lng": -99.9018131},
        "Nevada,United States": {"lat": 38.8026097, "lng": -116.419389},
        "New Hampshire,United States": {"lat": 43.1938516, "lng": -71.5723953},
        "New Jersey,United States": {"lat": 40.0583238, "lng": -74.4056612},
        "New Mexico,United States": {"lat": 34.5199402, "lng": -105.8700901},
        "New York,United States": {"lat": 40.7143528, "lng": -74.00597309999999},
        "North Carolina,United States": {"lat": 35.7595731, "lng": -79.01929969999999},
        "North Dakota,United States": {"lat": 47.5514926, "lng": -101.0020119},
        "Ohio,United States": {"lat": 40.4172871, "lng": -82.90712300000001},
        "Oklahoma ,United States": {"lat": 35.0077519, "lng": -97.092877},
        "Oregon,United States": {"lat": 43.8041334, "lng": -120.5542012},
        "Pennsylvania,United States": {"lat": 41.2033216, "lng": -77.1945247},
        "Puerto Rico,United States": {"lat": 26.1280581, "lng": -98.00728310000001},
        "Rhode Island,United States": {"lat": 41.5800945, "lng": -71.4774291},
        "South Carolina,United States": {"lat": 33.836081, "lng": -81.1637245},
        "South Dakota,United States": {"lat": 43.9695148, "lng": -99.9018131},
        "Tennessee,United States": {"lat": 35.5174913, "lng": -86.5804473},
        "Texas,United States": {"lat": 31.9685988, "lng": -99.9018131},
        "Utah,United States": {"lat": 39.3209801, "lng": -111.0937311},
        "Vermont,United States": {"lat": 44.5588028, "lng": -72.57784149999999},
        "Virginia,United States": {"lat": 37.4315734, "lng": -78.6568942},
        "Washington,United States": {"lat": 38.8951118, "lng": -77.0363658},
        "West Virginia,United States": {"lat": 38.5976262, "lng": -80.4549026},
        "Wisconsin,United States": {"lat": 43.7844397, "lng": -88.7878678},
        "Wyoming,United States": {"lat": 43.0759678, "lng": -107.2902839},
        "Uruguay": {"lat": -32.522779, "lng": -55.765835},
        "Uzbekistan": {"lat": 41.377491, "lng": 64.585262},
        "Vanuatu": {"lat": -15.376706, "lng": 166.959158},
        "Vatican City": {"lat": 41.902916, "lng": 12.453389},
        "Venezuela": {"lat": 6.42375, "lng": -66.58973},
        "Vietnam": {"lat": 14.058324, "lng": 108.277199},
        "Wallis & Futuna": {"lat": -14.2938, "lng": -178.1165},
        "Western Samoa": {"lat": -13.759029, "lng": -172.104629},
        "Yemen": {"lat": 15.552727, "lng": 48.516388},
        "Zambia": {"lat": -13.133897, "lng": 27.849332},
        "Zimbabwe": {"lat": -19.015438, "lng": 29.154857}
    };

    // linking the key-value-pairs is optional
    // if no argument is provided, linkItems === undefined, i.e. !== false
    // --> linking will be enabled
    function Map(linkItems) {
        this.current = undefined;
        this.size = 0;
        if(linkItems === false)
            this.disableLinking();
    }
    Map.noop = function() {
        return this;
    };
    Map.illegal = function() {
        throw new Error("illegal operation for maps without linking");
    };
    // map initialisation from existing object
    // doesn't add inherited properties if not explicitly instructed to:
    // omitting foreignKeys means foreignKeys === undefined, i.e. == false
    // --> inherited properties won't be added
    Map.from = function(obj, foreignKeys) {
        var map = new Map;
        for(var prop in obj) {
            if(foreignKeys || obj.hasOwnProperty(prop))
                map.put(prop, obj[prop]);
        }
        return map;
    };
    Map.prototype.disableLinking = function() {
        this.link = Map.noop;
        this.unlink = Map.noop;
        this.disableLinking = Map.noop;
        this.next = Map.illegal;
        this.key = Map.illegal;
        this.value = Map.illegal;
        this.removeAll = Map.illegal;
        return this;
    };
    // overwrite in Map instance if necessary
    Map.prototype.hash = function(value) {
        return (typeof value) + ' ' + (value instanceof Object ?
                        (value.__hash || (value.__hash = ++arguments.callee.current)) :
                        value.toString());
    };
    Map.prototype.hash.current = 0;
    // --- mapping functions
    Map.prototype.get = function(key) {
        var item = this[this.hash(key)];
        return item === undefined ? undefined : item.value;
    };
    Map.prototype.put = function(key, value) {
        var hash = this.hash(key);
        if(this[hash] === undefined) {
            var item = { key : key, value : value };
            this[hash] = item;
            this.link(item);
            ++this.size;
        }
        else this[hash].value = value;
        return this;
    };
    Map.prototype.remove = function(key) {
        var hash = this.hash(key);
        var item = this[hash];
        if(item !== undefined) {
            --this.size;
            this.unlink(item);
            delete this[hash];
        }
        return this;
    };
    // only works if linked
    Map.prototype.removeAll = function() {
        while(this.size)
            this.remove(this.key());
        return this;
    };
    // --- linked list helper functions
    Map.prototype.link = function(item) {
        if(this.size == 0) {
            item.prev = item;
            item.next = item;
            this.current = item;
        }
        else {
            item.prev = this.current.prev;
            item.prev.next = item;
            item.next = this.current;
            this.current.prev = item;
        }
    };
    Map.prototype.unlink = function(item) {
        if(this.size == 0)
            this.current = undefined;
        else {
            item.prev.next = item.next;
            item.next.prev = item.prev;
            if(item === this.current)
                this.current = item.next;
        }
    };
    // --- iterator functions - only work if map is linked
    Map.prototype.next = function() {
        this.current = this.current.next;
    };
    Map.prototype.key = function() {
        return this.current.key;
    };
    Map.prototype.value = function() {
        return this.current.value;
    };
</script>

<script type="text/javascript">
    var liferay_ws = new WebSocket('${websocketURL}');
    var addressCache = Map.from(world);
    var pointArray;
//    function switchSource() {
//        liferay_ws.send(JSON.stringify({command: 'switch'}));
//    }
    function clearMap() {
        pointArray.clear();
    }
    function initialize() {
        var map;
        pointArray = new google.maps.MVCArray([]);
        var mapOptions = {
            zoom             : 2,
            mapTypeId        : google.maps.MapTypeId.ROADMAP,
            center           : new google.maps.LatLng(51.507335, -0.127683)
        };
        map = new google.maps.Map(document.getElementById("${namespace}map_canvas"),
                mapOptions);
        var heatmap = new google.maps.visualization.HeatmapLayer({
            data: pointArray,
            radius: 5,
            maxIntensity: 10,
            dissipating: false
        });
        heatmap.setMap(map);
        liferay_ws.onmessage = function (evt) {
            var msg = JSON.parse(evt.data);
            var addr = msg.address;
            getLatLongForAddress(addr, function (coord) {
                var newCoord = new google.maps.LatLng(coord.lat * (0.95 + (Math.random() * 0.1)),
                        coord.lng * (0.95 + (Math.random() * 0.1)));
                pointArray.push(newCoord);
            });
        };
    }
    function getLatLongForAddress(city, callback) {
        if (addressCache.get(city)) {
            callback(addressCache.get(city));
        } else {
            var geocoder = new google.maps.Geocoder();
            geocoder.geocode({'address': city}, function (results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    var ll = {
                        lat: results[0].geometry.location.lat(),
                        lng: results[0].geometry.location.lng()
                    }
                    addressCache.put(city, ll);
                    callback(ll);
                } else {
                    console.log("failed to geocode " + city + ": " + status);
                }
            });
        }
    }
</script>

<script type="text/javascript" src="http://maps.google.com/maps/api/js?callback=initialize&sensor=false&libraries=visualization"></script>

<h2>WebSockets Demo using Liferay and Node.js</h2>
<button onclick="clearMap();">Clear Map</button>
<%--<button onclick="switchSource();">Java/Node Switch</button>--%>
<div id="${namespace}map_canvas" style="width: 800px; height: 550px;">map</div>


