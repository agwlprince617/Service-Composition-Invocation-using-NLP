import spacy
import requests
import os
import itertools
import numpy as np
from flask import Flask,render_template,request
nlp = spacy.load("../en_core_web_md")

app=Flask(__name__)



@app.route("/", methods=['GET', 'POST'])
def index():
    errors = []
    top_matching_descriptions = {}
    if request.method == "POST":
        # get url that the user has entered
        try:
            text1 = request.form['description']
           
            # print(text1)
        except:
            errors.append(
                "Unable to get Similarity Scores. Please make sure the description is valid and try again."
            )
            return render_template('index.html', errors=errors)
        if text1:
            text1.lower()
            text2=(
            "Get Flight Details to track delayed baggage."
            "This is the IATA code of arrival Airport."
            "The origin place."
            "The Flight number to track delayed baggage."
            "The destination place."
            "Get Passenger Details to track delayed baggage."
            "The return date."
            "This can be either Economy or Business or First or Premium_Economy."
            "List of Airlines involved."
            "Payment methods that can be used."
            "Terms & conditions on fare."
            "Departure & arrival cities involved."
            "Departure & arrival airports involved."
            "This is the number of adults."
            "This is the number of children."
            "Get Baggage Details to track delayed baggage."
            "Total number of baggages."
            "Number of checked in passengers."
            )
            text2.lower()
            text2_doc=nlp(text2)
            sentences=list(text2_doc.sents)

            def text_preprocessing(text):
                doc=nlp(text)
                result=[]
                for token in doc:
                    if token.is_punct:
                        continue
                    if token.lemma_ == '-PRON-':
                        continue
                    if token.text in nlp.Defaults.stop_words:
                        continue
                    result.append(token.lemma_)
                return " ".join(result)
            
            def calculate_similarity(text1,text2):
                base_word=text_preprocessing(text1)
                bw=nlp(base_word)
                compare=text_preprocessing(text2)
                cw=nlp(compare)
                return bw.similarity(cw)
            
            def sort_dict_by_value(d, reverse = False):
                return dict(sorted(d.items(), key = lambda x: x[1], reverse = reverse))
           
            answer={}
            # scores=[]
            for sentence in sentences:
                num=calculate_similarity(text1,str(sentence))
                num*=100
                new_num="{:.2f}".format(num)
                answer[sentence]=new_num
                # scores.append([num*100])

            # scores.sort(reverse=True)
            # scores=scores[:5]
            top_matching_descriptions={}
            top_matching_descriptions= sort_dict_by_value(answer,True)
            top_matching_descriptions=dict(itertools.islice(top_matching_descriptions.items(), 4))
    
    return render_template('index.html', errors=errors,top_matching_descriptions=top_matching_descriptions)




if __name__=="__main__":
    app.run(debug=True)