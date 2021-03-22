package com.romaingoguet.android.blood.data.models;


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Quizz {

    @SerializedName("questionnaire")
    @Expose
    private List<Questionnaire> questionnaire;

    public List<Questionnaire> getQuestionnaire() {
        return questionnaire;
    }

    public class Questionnaire {

        @SerializedName("question")
        @Expose
        private String question;
        @SerializedName("choice")
        @Expose
        private List<Choice> choice;

        public String getQuestion() {
            return question;
        }

        public List<Choice> getChoice() {
            return choice;
        }

    }


    public class Choice {

        @SerializedName("label")
        @Expose
        private String label;
        @SerializedName("response")
        @Expose
        private String response;

        public String getLabel() {
            return label;
        }

        public String getResponse() {
            return response;
        }

    }

}

