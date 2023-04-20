import spacy
import itertools
import numpy as np
nlp = spacy.load("en_core_web_md")



#User enters a query
text1=input("Enter the Api description to match against existing Api's description:\n")
text1.lower()


#Existing API description
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




#Converting the existing description texts into sentences
text2.lower()
text2_doc=nlp(text2)
# print(text2_doc)
sentences=list(text2_doc.sents)
# print(sentences)
 


#Include Stemming 

#Removing stopwords,punctuations and pronoun
def text_preprocessing(text):
    # doc= nlp(text.lower())
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





# Calculating the similarity between two texts
def calculate_similarity(text1,text2):
    # base_word=nlp(text_preprocessing(text1))
    # compare=nlp(text_preprocessing(text2))
    base_word=text_preprocessing(text1)
    bw=nlp(base_word)
    compare=text_preprocessing(text2)
    cw=nlp(compare)
    return bw.similarity(cw)




#Create a dictionary
#store all the values
#Sort according to the key pairs
#Slice the dictionary


#Calculating the similarity between the text descriptions
answer={}
for sentence in sentences:
    num=calculate_similarity(text1,str(sentence))
    answer[sentence]=num



#Sorting Function to get top matching dscriptions
def sort_dict_by_value(d, reverse = False):
  return dict(sorted(d.items(), key = lambda x: x[1], reverse = reverse))
    


#Returning the top descriptions after sorting
top_matching_descriptions={}
top_matching_descriptions= sort_dict_by_value(answer,True)
print(dict(itertools.islice(top_matching_descriptions.items(), 4)))














# If any issue look into this 
# pip install https://github.com/explosion/spacy-models/releases/download/en_core_web_md-3.5.0/en_core_web_md-3.5.0.tar.gz


