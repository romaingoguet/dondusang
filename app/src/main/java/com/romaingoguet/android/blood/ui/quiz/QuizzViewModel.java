package com.romaingoguet.android.blood.ui.quiz;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;
import com.romaingoguet.android.blood.data.models.Quizz;
import com.romaingoguet.android.blood.data.repo.QuizzRepository;
import java.util.List;

public class QuizzViewModel extends AndroidViewModel {

    private QuizzRepository mQuizzRepository;
    private MutableLiveData<Quizz> questionnaire;
    private MutableLiveData<List<Quizz.Questionnaire>> questions;
    private MutableLiveData<Integer> questionNo;
    private MutableLiveData<List<Quizz.Choice>> choices;
    private MutableLiveData<Boolean> isVisibleResponse;
    public QuizzViewModel(@NonNull Application application) {
        super(application);
        mQuizzRepository = new QuizzRepository();
        questionnaire = mQuizzRepository.getQuizz();

    }

    public MutableLiveData<List<Quizz.Questionnaire>> getQuestions() {
        if (questions == null) {
            questions = new MutableLiveData<>();
        }
        return questions;
    }

    public MutableLiveData<Integer> getQuestionNo() {
        if (questionNo == null) {
            questionNo = new MutableLiveData<Integer>();
        }
        return questionNo;
    }

    public MutableLiveData<Quizz> getQuestionnaire() {
        if (questionnaire == null) {
            questionnaire = new MutableLiveData<Quizz>();
        }
        return mQuizzRepository.getQuizz();
    }


    public MutableLiveData<List<Quizz.Choice>> getChoices() {
        if (choices == null) {
            choices = new MutableLiveData<List<Quizz.Choice>>();
        }
        choices.setValue(questionnaire.getValue().getQuestionnaire().get(getQuestionNo().getValue()).getChoice());
        return choices;
    }

}
