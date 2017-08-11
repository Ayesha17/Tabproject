package com.example.ayeshashahid.tabproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.LightingColorFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link TabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String course_list_url="http://192.168.1.24/course_service/Course_service.asmx/Course_list";
    JSONObject jsonobject,result;
    JSONArray jsonarray,jArray;
    String course_image;
    ArrayList<Book> books  = new ArrayList<Book>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View rootView;
    ProgressBar pb;

    //   private OnFragmentInteractionListener mListener;

    public TabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TabFragment newInstance(String param1, String param2) {
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_tab, container, false);
        new AsyncTaskParseJson().execute();
        Button create_new_course=(Button)rootView.findViewById(R.id.new_course);
        create_new_course.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x003DABFA));
        Button request=(Button)rootView.findViewById(R.id.request);
        request.getBackground().setColorFilter(new LightingColorFilter(0x00737373, 0x00D1D1D1));
    //    request.setBackgroundColor(getResources().getColor(R.color.buttonColor2));D1D1D1

//        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
//    // rv.setNestedScrollingEnabled(false);
//       rv.setHasFixedSize(true);
//        MyAdapter adapter = new MyAdapter(new String[]{"test one", "test two", "test three", "test four", "test five" ,"test one", "test two", "test three", "test four", "test five" , "test six" , "test seven", "test six" , "test seven"});
//        rv.setAdapter(adapter);
//
//        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
//        rv.setLayoutManager(llm);

        return rootView;


    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }



    public class AsyncTaskParseJson extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
//             pb = (ProgressBar) rootView.findViewById(R.id.pbLoading);
//            pb.setVisibility(ProgressBar.VISIBLE);

        }
        @Override
        protected Void doInBackground(Void... params) {


            // JSON file URL address
            result = JSONfunctions.getJSONfromURL(course_list_url);
            Log.d("jsonobject", String.valueOf(result));
            try {
                jArray = result.getJSONArray("course_info");
                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject jObject = jArray.getJSONObject(i);

                    Book book = new Book();
                    book.setName(jObject.getString("title"));
                    book.setCourse_category(jObject.getString("category"));
                    if (jObject.getString("tuition").equals("True")) {
                        book.setTutionfee("$" + jObject.getString("tuitionfee"));
                    } else
                        book.setTutionfee("Free");

                    if (jObject.getString("coursebanner").contains("data:image/jpeg;base64,"))
                        course_image = jObject.getString("coursebanner").replace("data:image/jpeg;base64,", "");

                    else
                        course_image = jObject.getString("coursebanner").replace("data:image/gif;base64,", "");

//      else
//                        course_image = jObject.getString("coursebanner").replace("data:image/png;base64,", "");
                    Log.d("image", course_image);

                    book.setImageUrl(course_image);
                    book.setAuthorName(jObject.getString("authorName"));

                    books.add(book);


                }

            } catch (JSONException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            //pb.setVisibility(ProgressBar.INVISIBLE);
            RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
//    // rv.setNestedScrollingEnabled(false);
       rv.setHasFixedSize(true);
        MyAdapter adapter = new MyAdapter(getActivity(),books);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
            rv.setLayoutManager(llm);
            rv.setAdapter(adapter);
//


        }
    }
}
