package com.romaingoguet.android.blood.data.repo;

import androidx.lifecycle.MutableLiveData;
import android.content.res.Resources;
import android.util.Log;
import com.google.gson.Gson;
import com.romaingoguet.android.blood.data.models.Quizz;
import java.io.IOException;
import java.io.InputStream;

public class QuizzRepository {

    private String quizzjson = "{\n" +
            "  \"questionnaire\": [\n" +
            "    {\n" +
            "      \"question\": \"Pesez-vous moins de 50kg ?\",\n" +
            "      \"choice\": [\n" +
            "        {\n" +
            "          \"label\": \"OUI\",\n" +
            "          \"response\": \"Vous devez peser plus de 50 kg car ce poids minimum garantit votre sécurité\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"label\": \"NON\",\n" +
            "          \"response\": \"OK\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"question\": \"Avez-vous déjà eu une transfusion de sang (globules rouges, plaquettes ou plasma) ou une greffe d’organe\",\n" +
            "      \"choice\": [\n" +
            "        {\n" +
            "          \"label\": \"OUI\",\n" +
            "          \"response\": \"Par mesure de précaution, le don de sang n’est pas possible actuellement si vous avez reçu une transfusion sanguine ou une greffe. Attention, il ne faut pas confondre transfusion et perfusion\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"label\": \"NON\",\n" +
            "          \"response\": \"OK\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"question\": \"Avez-vous une anémie ou un manque de fer ?\",\n" +
            "      \"choice\": [\n" +
            "        {\n" +
            "          \"label\": \"OUI\",\n" +
            "          \"response\": \"Si vous souffrez d’anémie, vous devez attendre que votre taux d’hémoglobine revienne à la normale pour donner votre sang\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"label\": \"NON\",\n" +
            "          \"response\": \"OK\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"question\": \"Êtes-vous enceinte ou avez-vous accouché depuis moins de 6 mois ?\",\n" +
            "      \"choice\": [\n" +
            "        {\n" +
            "          \"label\": \"OUI\",\n" +
            "          \"response\": \"Pour éviter tout risque de carence, vous ne pouvez pas donner si vous êtes enceinte. Et vous devez attendre 6 mois après l’accouchement\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"label\": \"NON\",\n" +
            "          \"response\": \"OK\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"question\": \"Avez-vous ou avez-vous eu de la fièvre ou une infection (toux, diarrhée, infection urinaire, plaie cutanée…) dans les 2 dernières semaines ?\",\n" +
            "      \"choice\": [\n" +
            "        {\n" +
            "          \"label\": \"OUI\",\n" +
            "          \"response\": \"Il faut attendre 15 jours après la disparition des symptômes pour donner son sang\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"label\": \"NON\",\n" +
            "          \"response\": \"OK\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"question\": \"Avez-vous eu des soins dentaires depuis moins de 24 heures (carie, détartrage), un traitement de racine ou une extraction dentaire depuis moins d’une semaine ?\",\n" +
            "      \"choice\": [\n" +
            "        {\n" +
            "          \"label\": \"OUI\",\n" +
            "          \"response\": \"Le don de sang est possible un jour après un détartrage ou le traitement d’une carie et une semaine après une extraction dentaire\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"label\": \"NON\",\n" +
            "          \"response\": \"OK\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"question\": \"Avez-vous été opéré(e) ou subi une endoscopie (fibroscopie gastrique, coloscopie…) dans les 4 derniers mois ?\",\n" +
            "      \"choice\": [\n" +
            "        {\n" +
            "          \"label\": \"OUI\",\n" +
            "          \"response\": \"Il faut attendre au moins 4 mois après l’opération ou l’endoscopie pour donner son sang\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"label\": \"NON\",\n" +
            "          \"response\": \"OK\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"question\": \"Avez-vous eu un piercing (boucle d’oreille compris) ou un tatouage dans les 4 derniers mois ?\",\n" +
            "      \"choice\": [\n" +
            "        {\n" +
            "          \"label\": \"OUI\",\n" +
            "          \"response\": \"Pour éviter tout risque d’infection lié à une mauvaise stérilisation du matériel, vous devez attendre 4 mois après un piercing ou un tatouage pour donner votre sang\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"label\": \"NON\",\n" +
            "          \"response\": \"OK\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"question\": \"Avez-vous eu récemment des douleurs cardiaques après un effort ?\",\n" +
            "      \"choice\": [\n" +
            "        {\n" +
            "          \"label\": \"OUI\",\n" +
            "          \"response\": \"Pour garantir la sécurité du donneur, le don de sang n’est pas possible quand on a des problèmes cardiaques ou que l’on a été victime d’un AVC\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"label\": \"NON\",\n" +
            "          \"response\": \"OK\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"question\": \"Avez-vous eu plus d’un partenaire sexuel dans les 4 derniers mois ?\",\n" +
            "      \"choice\": [\n" +
            "        {\n" +
            "          \"label\": \"OUI\",\n" +
            "          \"response\": \"Vous ne pouvez pas donner si vous avez eu une relation sexuelle, même protégée, avec plus d’un partenaire au cours des 4 derniers mois. Cette contre-indication ne s’applique pas aux femmes ayant eu des relations sexuelles uniquement avec des femmes\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"label\": \"NON\",\n" +
            "          \"response\": \"OK\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"question\": \"Avez-vous déjà pris des drogues par voie intraveineuse ?\",\n" +
            "      \"choice\": [\n" +
            "        {\n" +
            "          \"label\": \"OUI\",\n" +
            "          \"response\": \"Tout antécédent d'injection de produits stupéfiants par voie intraveineuse, même remontant à plusieurs années, constitue une contre-indication permanente au don de sang\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"label\": \"NON\",\n" +
            "          \"response\": \"OK\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"question\": \"Avez-vous connaissance de pratiques à risques chez votre partenaire (multipartenaires, drogues par voie intraveineuse) ?\",\n" +
            "      \"choice\": [\n" +
            "        {\n" +
            "          \"label\": \"OUI\",\n" +
            "          \"response\": \"Selon le cas, vous devez attendre de 4 à 12 mois après le dernier contact sexuel avec ce partenaire\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"label\": \"NON\",\n" +
            "          \"response\": \"OK\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"question\": \"Avez-vous voyagé dans les 4 derniers mois ?\",\n" +
            "      \"choice\": [\n" +
            "        {\n" +
            "          \"label\": \"OUI\",\n" +
            "          \"response\": \"Des délais de 1 à 4 mois sont à respecter au retour de certains pays.\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"label\": \"NON\",\n" +
            "          \"response\": \"OK\"\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    public MutableLiveData<Quizz> getQuizz() {
        final MutableLiveData<Quizz> quizz = new MutableLiveData<Quizz>();
        Gson gson = new Gson();
        quizz.setValue(gson.fromJson(quizzjson, Quizz.class));
        return quizz;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = Resources.getSystem().getAssets().open("quiz.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            Log.d("lol", "loadJSONFromAsset: " + ex);
            ex.printStackTrace();
            return null;
        } catch (NullPointerException e) {
            Log.d("lol", "loadJSONFromAsset: " + e);
            return null;
        }
        return json;
    }
}
