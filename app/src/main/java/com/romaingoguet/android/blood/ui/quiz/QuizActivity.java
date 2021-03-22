package com.romaingoguet.android.blood.ui.quiz;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.romaingoguet.android.blood.R;
import com.romaingoguet.android.blood.databinding.ActivityQuizBinding;
import com.romaingoguet.android.blood.data.models.Quizz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class QuizActivity extends AppCompatActivity {

    private static String TAG = "lol";
    private int questionNo;
    private Quizz quiz;
    private QuizzViewModel mQuizzViewModel;
    private ActivityQuizBinding binding;
    private int nbQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Change the title
        setTitle("Puis-donner ?");

        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz);


        // get the viewModel
        mQuizzViewModel = ViewModelProviders.of(this).get(QuizzViewModel.class);
        // we create the binding and we do all the binding in the layout
        //binding = DataBindingUtil.setContentView(this,R.layout.activity_quiz);
        // set the values for the viewModel (question number)
        mQuizzViewModel.getQuestionNo().setValue(questionNo);

        //get the number of question in the quiz
        nbQuestions = mQuizzViewModel.getQuestionnaire().getValue().getQuestionnaire().size();

        // bind the liveData from viewmodel in the view
        binding.setQuestion(mQuizzViewModel.getQuestionnaire().getValue().getQuestionnaire().get(questionNo));
        binding.setQuestionHeader(getHeaderQuestion());
        binding.setQuiz(mQuizzViewModel);
        binding.setIsVisibleResponse(false);
        binding.setIsVisibleNo(true);
        binding.setIsVisibleYes(true);
        binding.setIsVisibleRetry(false);
        //bind the handlers for the event (clicks) in the view
        QuizActivity.MyHandlers handlers = new QuizActivity.MyHandlers();
        binding.setHandlers(handlers);

        binding.setLifecycleOwner(this);

        // create the obervers
        mQuizzViewModel.getQuestionNo().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                binding.setQuestion(mQuizzViewModel.getQuestionnaire().getValue().getQuestionnaire().get(questionNo));
                Log.d(TAG, "onChanged: " + mQuizzViewModel.getQuestionnaire().getValue().getQuestionnaire().get(questionNo).getChoice().get(0).getResponse());
                binding.setChoice(mQuizzViewModel.getQuestionnaire().getValue().getQuestionnaire().get(questionNo).getChoice().get(0));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private String getHeaderQuestion() {
        return (questionNo + 1) + " / " + nbQuestions;
    }


    public class MyHandlers {

        public void onClickQuestionNo(View view) {
            questionNo++;
            if (questionNo < nbQuestions) {
                mQuizzViewModel.getQuestionNo().setValue(questionNo);
                binding.setQuestionHeader(getHeaderQuestion());
                Log.d("lol", "onClickQuestionYes: " + mQuizzViewModel.getQuestionNo().getValue());
            } else {
                //show the final screen
                binding.setIsVisibleCanDonate(true);
                binding.setIsVisibleYes(false);
                binding.setIsVisibleNo(false);
                binding.setIsVisibleRetry(true);
                binding.setIsVisibleResponse(false);
            }
        }

        public void onClickQuestionYes(View view) {
            Log.d(TAG, "onClickQuestionNo: noooo");
            binding.setIsVisibleResponse(true);
            binding.setIsVisibleNo(false);
            binding.setIsVisibleYes(false);
            binding.setIsVisibleRetry(true);
        }

        public void onClickRetry(View view) {
            Log.d(TAG, "onClickRetry: retry");
            binding.setIsVisibleCanDonate(false);
            binding.setIsVisibleResponse(false);
            binding.setIsVisibleNo(true);
            binding.setIsVisibleYes(true);
            binding.setIsVisibleRetry(false);
            questionNo = 0;
            binding.setQuestionHeader(getHeaderQuestion());
            mQuizzViewModel.getQuestionNo().setValue(questionNo);
        }
    }

}
