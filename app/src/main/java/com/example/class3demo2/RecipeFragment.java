package com.example.class3demo2;

import static com.example.class3demo2.MyApplication.getAppContext;
import static com.example.class3demo2.model.Model.isOnline;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.class3demo2.databinding.FragmentAddRecipeBinding;
import com.example.class3demo2.databinding.FragmentRecipePageBinding;
import com.example.class3demo2.model.Model;
import com.example.class3demo2.model.Recipe;
import com.squareup.picasso.Picasso;


// recipe
public class RecipeFragment extends Fragment {
    String title;
    String ingredients;
    String instructions;
    String avatarImg;
    FragmentRecipePageBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  //save state
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentRecipePageBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        //save element argument
        getElement();

        // **********set the details recipe into ui ***********
        if (title != null){
            binding.recipeTitleTv.setText(title);
        }
        if (ingredients != null){
            binding.IngredientsTv.setText(ingredients);
        }
        if (instructions != null){
            binding.InstructionsTv.setText(instructions);
        }
        if (avatarImg.isEmpty() || avatarImg ==""){
            binding.avatarImg.setImageResource(R.drawable.meal2);

        }else{
            Picasso.get().load(avatarImg).error(R.drawable.error).into(binding.avatarImg);
        }

        //******* get the status of like recipe and set him to ui *********
        if(isOnline(getAppContext())) {
            Model.instance().getAllLikes(likes -> {
                if (likes.contains(title))
                    binding.like.setChecked(true);
                else {
                    binding.like.setChecked(false);
                }
            });
        }

        binding.comBtn.setOnClickListener((view1)->{
            // TODO: go to comments fragment
        });


        binding.backBtn.setOnClickListener((view1)->{
            Navigation.findNavController(view1).popBackStack();
        });

        // *********** when click like or unlike >> update the likes if firebase *****
        binding.like.setOnClickListener(view1->{
            Model.instance().saveLike(title);
        });

        // *************** rest api ***************
//        binding.translateBtn.setOnClickListener(view1->{
//
//            //send the name of recipe to api and get the translate
//            Model.instance().restApi(binding.recipeTitleTv.getText().toString(), trans->{
//                binding.recipeTitleTv.setText(trans.toString());
//
//            });
//        });
        return view;

    }

    public void getElement() {
        title = RecipeFragmentArgs.fromBundle(getArguments()).getNameRecipe();
        ingredients = RecipeFragmentArgs.fromBundle(getArguments()).getIngredients();
        instructions = RecipeFragmentArgs.fromBundle(getArguments()).getInstructions();
        avatarImg = (RecipeFragmentArgs.fromBundle(getArguments()).getAvatarUrl());

    }
}